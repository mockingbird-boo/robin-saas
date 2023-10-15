package cn.com.mockingbird.robin.web.util;

import org.slf4j.event.Level;

import java.lang.annotation.*;

/**
 * 方法追踪注解
 * <p>
 * 在需要进行执行时长等信息追踪的方法上使用该注解，即会自动完成追踪信息的日志打印
 *
 * @author zhaopeng
 * @date 2023/10/3 15:22
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Trace {

    /**
     * 日志级别
     * @return 级别枚举
     */
    Level level() default Level.DEBUG;

    /**
     * 描述
     * @return 描述信息
     */
    String description() default "";

}
