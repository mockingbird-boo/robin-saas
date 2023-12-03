package cn.com.mockingbird.robin.api.aspect;

import cn.com.mockingbird.robin.api.annotation.ApiSecurity;
import cn.com.mockingbird.robin.api.autoconfigure.ApiSecurityProperties;
import cn.com.mockingbird.robin.api.model.SafeResponseData;
import cn.com.mockingbird.robin.common.util.encrypt.AesUtils;
import cn.com.mockingbird.robin.common.util.encrypt.RsaUtils;
import cn.com.mockingbird.robin.common.util.response.ResponseData;
import com.alibaba.fastjson2.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.security.PrivateKey;

/**
 * API 响应数据增强类，支持脱敏和加密
 * TODO 需要验证当前 Advice 是否先执行于 cn.com.mockingbird.robin.web.mvc.ResponseDataAdvice
 *
 * @author zhaopeng
 * @date 2023/11/20 16:13
 **/
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseDataSecurityAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private ApiSecurityProperties apiSecurityProperties;

    @SuppressWarnings("all")
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ApiSecurity.class) || returnType.hasMethodAnnotation(ApiSecurity.class);
    }

    @SuppressWarnings("all")
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Method method = returnType.getMethod();
        Assert.notNull(method, "method requires not null");
        ApiSecurity apiSecurity = method.getAnnotation(ApiSecurity.class);
        if (apiSecurity == null) {
            apiSecurity = method.getDeclaringClass().getAnnotation(ApiSecurity.class);
        }
        if (body != null && apiSecurity != null && apiSecurity.encryptResponse() && apiSecurityProperties.getEnable()) {
            if (body instanceof ResponseData responseData) {
                body = responseData.getData();
            }
            body = encryptResponseData(body);
        }
        return body;
    }

    /**
     * 加密响应数据
     * @param responseData 要加密的响应数据
     * @return 加密结果
     */
    private Object encryptResponseData(Object responseData) {
        String responseJson = JSONObject.toJSONString(responseData);
        String aesKey = AesUtils.generateAesKey();
        String encryptedData = AesUtils.encrypt(responseJson, aesKey);
        PrivateKey privateKey = RsaUtils.getPrivateKey(apiSecurityProperties.getServerKeyPair().getPrivateKey());
        String encryptedKey = RsaUtils.encrypt(aesKey, privateKey);
        return new SafeResponseData(encryptedKey, encryptedData);
    }
}
