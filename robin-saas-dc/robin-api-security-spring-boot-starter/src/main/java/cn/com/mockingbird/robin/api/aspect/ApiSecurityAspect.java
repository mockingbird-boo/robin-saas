package cn.com.mockingbird.robin.api.aspect;

import cn.com.mockingbird.robin.api.annotation.ApiSecurity;
import cn.com.mockingbird.robin.api.exception.ApiSecurityException;
import cn.com.mockingbird.robin.api.model.ApiSecurityParam;
import cn.com.mockingbird.robin.api.wrapper.HttpServletRequestBodyWrapper;
import cn.com.mockingbird.robin.common.util.encrypt.RsaUtils;
import cn.com.mockingbird.robin.common.util.request.RequestUtils;
import cn.com.mockingbird.robin.web.mvc.ResponseCode;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * ApiSecurity 注解的切面
 *
 * @author zhaopeng
 * @date 2023/10/31 2:13
 **/
@Slf4j
@Aspect
public class ApiSecurityAspect {

    @Around(value = "@annotation(apiSecurity)")
    public Object beforeApiProcess(ProceedingJoinPoint joinPoint, ApiSecurity apiSecurity) throws Throwable {
        HttpServletRequest request = RequestUtils.getHttpRequest();
        // 只有 POST/PUT/PATCH 相关的 API 才需要参数加密加签
        if (!RequestUtils.isPostOrPutOrPatchRequest(request)) {
            throw new ApiSecurityException(ResponseCode.METHOD_NOT_ALLOWED.getCode(), ResponseCode.METHOD_NOT_ALLOWED.getMessage());
        }
        if (apiSecurity.decrypt()) {
            Object[] args = joinPoint.getArgs(), decryptedArgs;
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
                ApiSecurityParam apiSecurityParam = JSONObject.parseObject(requestBody, ApiSecurityParam.class);
                // RSA 私钥解密得到 AES 密钥
                String aesKey = RsaUtils.decrypt(apiSecurityParam.getKey(), RsaUtils.getPrivateKey(""));
                // AES 密钥解密 Data 数据
                String data = "";
                // 接口参数类的类对象
                Class<?> clazz = args[0].getClass();
                Object param = JSONObject.parseObject(data, clazz);
                decryptedArgs = new Object[]{param};
                return joinPoint.proceed(decryptedArgs);
            }
        }
        return null;
    }
}
