package cn.com.mockingbird.robin.api.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * API 安全注解
 * <p>
 * 该注解用于标识 API 接口会经过加密、加签来加固接口的安全性。
 * 安全性包括：防篡改、防重放、响应数据脱敏。
 *
 * @author zhaopeng
 * @date 2023/10/28 1:09
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ApiSecurity {

    @AliasFor("signature")
    boolean value() default true;

    /**
     * 是否需要签名
     */
    @AliasFor("value")
    boolean signature() default true;

    /**
     * 请求参数是否需要解密
     */
    boolean decryptForRequest() default false;

    /**
     * 响应数据是否需要加密
     */
    boolean encryptForResponse() default false;

}
