package cn.com.mockingbird.robin.web.desensitize;

import cn.com.mockingbird.robin.common.constant.Standard;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.*;

/**
 * 脱敏注解
 *
 * @author zhaopeng
 * @date 2023/11/21 0:47
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizeSerializer.class)
public @interface Desensitize {

    /**
     * 脱敏类型
     */
    DesensitizeType type();

    /**
     * 前置保留长度
     */
    int prefix() default 1;

    /**
     * 后置保留长度
     */
    int suffix() default 1;

    /**
     * 打码符号，默认：“*”
     */
    String mark() default Standard.Str.STAR;

}
