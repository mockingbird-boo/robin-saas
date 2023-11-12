package cn.com.mockingbird.robin.dynamic.datasource.aop;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 动态数据源注解
 *
 * @author zhaopeng
 * @date 2023/11/12 19:06
 **/
@SuppressWarnings("unused")
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

    /**
     * 数据源 Key
     */
    @AliasFor("key")
    String value() default "";

    @AliasFor("value")
    String key() default "";

}
