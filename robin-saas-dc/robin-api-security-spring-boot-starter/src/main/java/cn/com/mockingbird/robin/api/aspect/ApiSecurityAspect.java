package cn.com.mockingbird.robin.api.aspect;

import cn.com.mockingbird.robin.api.annotation.ApiSecurity;
import cn.com.mockingbird.robin.api.autoconfigure.ApiSecurityProperties;
import cn.com.mockingbird.robin.api.enums.DigestAlgorithm;
import cn.com.mockingbird.robin.api.enums.EncryptAlgorithm;
import cn.com.mockingbird.robin.api.enums.IdempotentStrategy;
import cn.com.mockingbird.robin.api.exception.ApiSecurityException;
import cn.com.mockingbird.robin.api.model.RequestBodyData;
import cn.com.mockingbird.robin.api.wrapper.HttpServletRequestBodyWrapper;
import cn.com.mockingbird.robin.common.constant.Standard;
import cn.com.mockingbird.robin.common.user.UserHolder;
import cn.com.mockingbird.robin.common.util.BranchUtils;
import cn.com.mockingbird.robin.common.util.encrypt.AesUtils;
import cn.com.mockingbird.robin.common.util.encrypt.DigestUtils;
import cn.com.mockingbird.robin.common.util.encrypt.RsaUtils;
import cn.com.mockingbird.robin.common.util.request.RequestUtils;
import cn.com.mockingbird.robin.redis.core.service.RedisLockService;
import cn.com.mockingbird.robin.redis.core.service.RedisStringService;
import cn.com.mockingbird.robin.web.mvc.ResponseCode;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * ApiSecurity 注解的切面
 *
 * @author zhaopeng
 * @date 2023/10/31 2:13
 **/
@Slf4j
@Aspect
public class ApiSecurityAspect {

    private ApiSecurityProperties apiSecurityProperties;

    private RedisStringService redisStringService;

    private RedisLockService redisLockService;

    public ApiSecurityAspect() {}

    @SuppressWarnings("unused")
    public ApiSecurityAspect(ApiSecurityProperties apiSecurityProperties, RedisStringService redisStringService, RedisLockService redisLockService) {
        this.apiSecurityProperties = apiSecurityProperties;
        this.redisStringService = redisStringService;
        this.redisLockService = redisLockService;
    }

    @Around(value = "@annotation(apiSecurity)")
    public Object beforeApiProcess(ProceedingJoinPoint joinPoint, ApiSecurity apiSecurity) throws Throwable {
        HttpServletRequest request = RequestUtils.getHttpRequest();
        Object[] args = joinPoint.getArgs();
        RequestBodyData requestBodyData = null;
        // 如果请求参数的传输是加密的，则需要对请求参数进行解密
        if (apiSecurity.encrypted()) {
            // 注解使用不当，最终需要解密请求参数，然后需要序列化成实际的接口参数，多个参数无法支持
            if (args.length > 1) {
                throw new ApiSecurityException("请联系技术人员检查安全接口");
            }
            requestBodyData = getRequestBodyData(request);
            // 如果接口没有参数，或者请求没有请求体，那么解密没有意义
            if (args.length == 1 && requestBodyData != null) {
                Class<?> clazz = args[0].getClass();
                Object decryptedParamInstance = getDecryptedParamInstance(requestBodyData, apiSecurity.encryptAlgorithm(), clazz);
                args[0] = decryptedParamInstance;
            }
        }
        // 如果接口需要进行数字签名认证
        if (apiSecurity.signature()) {
            final String[] signature = new String[1];
            final String[] nonce = new String[1];
            final String[] timestamp = new String[1];
            // 如果请求参数的传输是加密的，则签名、唯一标识、时间戳等参数从解密的 requestBodyData 对象中获取
            BranchUtils.nonNullOrElse(requestBodyData).presentOrElseHandle(data -> {
                signature[0] = data.getSignature();
                nonce[0] = data.getNonce();
                timestamp[0] = data.getTimestamp();
            }, () -> {
                signature[0] = request.getHeader(Standard.RequestHeader.SIGNATURE);
                nonce[0] = request.getHeader(Standard.RequestHeader.NONCE);
                timestamp[0] = request.getHeader(Standard.RequestHeader.TIMESTAMP);
            });
            // 参数校验
            validSignatureFactor(signature[0], nonce[0], timestamp[0], apiSecurity.signatureValidityTime());

            String digest;
            try {
                // RSA 公钥解密获取摘要
                String publicKey = apiSecurityProperties.getClientKeyPair().getPublicKey();
                digest = RsaUtils.decrypt(signature[0], RsaUtils.getPublicKey(publicKey));
            } catch (Exception e) {
                log.warn("不合法的客户端，身份可能被伪装");
                throw new ApiSecurityException(ResponseCode.BAD_REQUEST.getCode(), "数字签名认证失败");
            }
            String decryptedData = requestBodyData == null ? Standard.Str.EMPTY : requestBodyData.getDecryptedData();
            // 摘要内容
            String digestContent = String.join(Standard.Str.AT, nonce[0], timestamp[0], decryptedData);
            // 完整性校验
            validDigest(digestContent, digest, apiSecurity.digestAlgorithm());
        }
        // 如果接口需要防重
        if (apiSecurity.idempotent()) {
            IdempotentStrategy strategy = apiSecurity.strategy();
            String username = UserHolder.getCurrentUser().getUsername();
            boolean isIdempotent = false;
            switch (strategy) {
                case NONCE -> {
                    final String nonce;
                    // 如果请求参数的传输是加密的，则签名、唯一标识、时间戳等参数从解密的 requestBodyData 对象中获取
                    if (requestBodyData != null) {
                        nonce = requestBodyData.getNonce();
                    } else {
                        nonce = request.getHeader(Standard.RequestHeader.NONCE);
                    }
                    validNonce(nonce);
                    String key = Key.generateNonceKey(nonce);
                    isIdempotent = redisStringService.setIfAbsent(key, 1, apiSecurity.idempotentValidityTime());
                }
                case LOCK -> {
                    String clientIp = RequestUtils.getClientIp(request);
                    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                    String className = method.getDeclaringClass().getName();
                    long interval = apiSecurity.idempotentValidityTime();
                    if (interval > 60 * 60 * 24) {
                        interval = 5;
                    }
                    RLock lock = redisLockService.getLock(Key.generateLockKey(username, clientIp, className, method.getName()));
                    // 加锁，并指定等待0秒和锁释放时间，成功加锁即非重复提交
                    isIdempotent = redisLockService.tryLock(lock, 0, interval);
                }
                case TOKEN -> {
                    String token;
                    if (requestBodyData != null) {
                        token = requestBodyData.getToken();
                    } else {
                        token = request.getHeader(Standard.RequestHeader.IDEMPOTENT_TOKEN);
                    }
                    if (StringUtils.isBlank(token)) {
                        throw new ValidationException("幂等防重 TOKEN 不能为空");
                    }
                    // 有幂等令牌就删，成功删掉即非重复提交
                    String key = Key.generateTokenKey(username, token);
                    isIdempotent = redisStringService.delete(key);
                }
            }
            if (!isIdempotent) {
                throw new ApiSecurityException(ResponseCode.CONFLICT.getCode(), "重复请求");
            }
        }
        return joinPoint.proceed(args);
    }

    private void validDigest(String content, String digest, DigestAlgorithm digestAlgorithm) {
        if (!DigestUtils.isOriginalContent(content, digest, digestAlgorithm.name())) {
            log.warn("摘要数据可能被篡改");
            throw new ApiSecurityException(ResponseCode.BAD_REQUEST.getCode(), "数字签名认证失败");
        }
    }

    private void validSignatureFactor(String signature, String nonce, String timestamp, long signatureValidityTime) {
        validSignature(signature);
        validNonce(nonce);
        validTimestamp(timestamp, signatureValidityTime);
    }

    private void validNonce(String nonce) {
        if (StringUtils.isBlank(nonce)) {
            throw new ValidationException("NONCE 不能为空");
        }
    }

    private void validTimestamp(String timestamp, long signatureValidityTime) {
        if (StringUtils.isBlank(timestamp)) {
            throw new ValidationException("时间戳不能为空");
        }
        long t;
        try {
            t = Long.parseLong(timestamp);
        } catch (NumberFormatException e) {
            log.error("非法的时间戳", e);
            throw new ValidationException("非法的时间戳");
        }
        long diff = ChronoUnit.SECONDS.between(Instant.ofEpochMilli(t), Instant.now());
        if (diff > signatureValidityTime) {
            throw new ValidationException("签名已经过期");
        }
    }

    private void validSignature(String signature) {
        if (StringUtils.isBlank(signature)) {
            throw new ValidationException("数据签名不能为空");
        }
    }

    private RequestBodyData getRequestBodyData(HttpServletRequest request) throws IOException {
        HttpServletRequestBodyWrapper requestWrapper;
        if (request instanceof HttpServletRequestBodyWrapper) {
            requestWrapper = (HttpServletRequestBodyWrapper) request;
        } else {
            requestWrapper = new HttpServletRequestBodyWrapper(request);
        }
        String requestBody = requestWrapper.getRequestBody();
        return JSONObject.parseObject(requestBody, RequestBodyData.class);
    }

    /**
     * 获取 API 接口解密后实际参数类型的实例
     * @param requestBodyData 加密请求实例
     * @param encryptAlgorithm 加密算法，解密的依据
     * @param clazz 通过分析切点得到的实际参数的类类型
     * @return 实际参数类型的实例
     */
    private Object getDecryptedParamInstance(RequestBodyData requestBodyData, EncryptAlgorithm encryptAlgorithm, Class<?> clazz) {
        String privateKey = apiSecurityProperties.getServerKeyPair().getPrivateKey();
        final String decryptedData = (String) BranchUtils
                .isTureOrFalseWithReturn(encryptAlgorithm == EncryptAlgorithm.RSA_AES)
                .trueOrFalseHandle(() -> {
                    // RSA 私钥解密得到 AES 密钥
                    String aesKey = RsaUtils.decrypt(requestBodyData.getKey(), RsaUtils.getPrivateKey(privateKey));
                    // AES 密钥解密请求数据得到原始的请求参数
                    return AesUtils.decrypt(requestBodyData.getData(), aesKey);
                }, () -> RsaUtils.decrypt(requestBodyData.getData(), RsaUtils.getPrivateKey(privateKey)));
        // 数字签名认证时会使用到
        requestBodyData.setDecryptedData(decryptedData);
        return JSONObject.parseObject(decryptedData, clazz);
    }
}
