package cn.com.mockingbird.robin.api.aspect;

import cn.com.mockingbird.robin.api.annotation.ApiSecurity;
import cn.com.mockingbird.robin.api.autoconfigure.ApiSecurityProperties;
import cn.com.mockingbird.robin.api.exception.ApiSecurityException;
import cn.com.mockingbird.robin.api.model.ApiRequestParam;
import cn.com.mockingbird.robin.api.wrapper.HttpServletRequestBodyWrapper;
import cn.com.mockingbird.robin.common.util.encrypt.AesUtils;
import cn.com.mockingbird.robin.common.util.encrypt.DigestUtils;
import cn.com.mockingbird.robin.common.util.encrypt.RsaUtils;
import cn.com.mockingbird.robin.common.util.request.RequestUtils;
import cn.com.mockingbird.robin.redis.core.service.RedisStringService;
import cn.com.mockingbird.robin.web.mvc.ResponseCode;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

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

    private final ApiSecurityProperties apiSecurityProperties;

    private final RedisStringService redisStringService;

    public ApiSecurityAspect(ApiSecurityProperties apiSecurityProperties, RedisStringService redisStringService) {
        this.apiSecurityProperties = apiSecurityProperties;
        this.redisStringService = redisStringService;
    }

    @Around(value = "@annotation(apiSecurity)")
    public Object beforeApiProcess(ProceedingJoinPoint joinPoint, ApiSecurity apiSecurity) throws Throwable {
        HttpServletRequest request = RequestUtils.getHttpRequest();
        // 只有 POST/PUT/PATCH 相关的 API 才可能需要参数加密加签
        if (!RequestUtils.isPostOrPutOrPatchRequest(request)) {
            throw new ApiSecurityException(ResponseCode.METHOD_NOT_ALLOWED.getCode(), ResponseCode.METHOD_NOT_ALLOWED.getMessage());
        }
        Object[] args = joinPoint.getArgs(), decryptedArgs;
        if (apiSecurity.encrypted()) {
            // 安全接口不支持多个方法参数，只支持一个参数（封装类）
            if (args.length > 1) {
                throw new ApiSecurityException("请联系技术人员检查安全接口");
            }
            if (args.length == 1) {
                HttpServletRequestBodyWrapper requestWrapper;
                if (request instanceof HttpServletRequestBodyWrapper) {
                    requestWrapper = (HttpServletRequestBodyWrapper) request;
                } else {
                    requestWrapper = new HttpServletRequestBodyWrapper(request);
                }
                String requestBody = requestWrapper.getRequestBody();
                ApiRequestParam apiRequestParam = JSONObject.parseObject(requestBody, ApiRequestParam.class);
                // RSA 私钥解密得到 AES 密钥
                String aesKey = RsaUtils.decrypt(apiRequestParam.getKey(), RsaUtils.getPrivateKey(apiSecurityProperties.getRsaPrivateKey()));
                // AES 密钥解密请求数据得到原始的请求参数
                String decryptedParams = AesUtils.decrypt(apiRequestParam.getData(), aesKey);
                // 接口参数类的类对象
                Class<?> clazz = args[0].getClass();
                Object param = JSONObject.parseObject(decryptedParams, clazz);
                decryptedArgs = new Object[]{param};
                return joinPoint.proceed(decryptedArgs);
            }
        }
        // 验签
        if (apiSecurity.signature()) {

        }
        return null;
    }

    /**
     * 加密加签验签，从 ApiSecurityParam 实例中获取签名、唯一标识、时间戳
     * @param apiRequestParam 请求参数
     * @param data 解密后的参数数据
     */
    private void validSignature(ApiRequestParam apiRequestParam, String data) {
        String signature = apiRequestParam.getSignature();
        String nonce = apiRequestParam.getNonce();
        String timestamp = apiRequestParam.getTimestamp();

        validSignature(signature);
        validTimestamp(timestamp);
        validNonce(nonce);

        if (DigestUtils.isOriginalContentByMd5(nonce + data + timestamp, signature)) {
            // 签名认证通过后，进行防重校验
            boolean isPrettyRequest = redisStringService.setIfAbsent(nonce, timestamp, apiSecurityProperties.getSignatureExpiredTime());
            if (!isPrettyRequest) {
                throw new ApiSecurityException(ResponseCode.CONFLICT.getCode(), "重复请求");
            }
        } else {
            log.warn("请求参数可能被篡改");
            throw new ApiSecurityException(ResponseCode.BAD_REQUEST.getCode(), "签名认证失败");
        }

    }

    private void validNonce(String nonce) {
        if (StringUtils.isBlank(nonce)) {
            throw new ApiSecurityException(ResponseCode.BAD_REQUEST.getCode(), "nonce 不能为空");
        }
    }

    private void validTimestamp(String timestamp) {
        if (StringUtils.isBlank(timestamp)) {
            throw new ApiSecurityException(ResponseCode.BAD_REQUEST.getCode(), "时间戳不能为空");
        }
        long t;
        try {
            t = Long.parseLong(timestamp);
        } catch (NumberFormatException e) {
            log.error("非法的时间戳", e);
            throw new ApiSecurityException(ResponseCode.BAD_REQUEST.getCode(), "非法的时间戳");
        }
        long diff = ChronoUnit.SECONDS.between(Instant.ofEpochMilli(t), Instant.now());
        if (diff > apiSecurityProperties.getSignatureExpiredTime()) {
            throw new ApiSecurityException(ResponseCode.BAD_REQUEST.getCode(), "签名已经过期");
        }
    }

    private void validSignature(String signature) {
        if (StringUtils.isBlank(signature)) {
            throw new ApiSecurityException(ResponseCode.BAD_REQUEST.getCode(), "数据签名不能为空");
        }
    }
}
