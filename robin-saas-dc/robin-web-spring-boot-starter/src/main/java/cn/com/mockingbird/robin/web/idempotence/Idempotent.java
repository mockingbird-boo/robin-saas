package cn.com.mockingbird.robin.web.idempotence;

import java.lang.annotation.*;

/**
 * 幂等注解
 * <p>
 * 用在具体的方法上标识该方法具有幂等特性，不支持重复调用
 *
 * @author zhaopeng
 * @date 2023/10/16 22:33
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Idempotent {

    /**
     * 实现策略：方法参数和令牌
     */
    enum Strategy {
        /**
         * 方法参数
         */
        PARAM,
        /**
         * 令牌
         */
        TOKEN
    }

    /**
     * 策略，默认为方法参数策略
     * @return 策略类型
     */
    Strategy strategy() default Strategy.PARAM;

    /**
     * 间隔，采用参数策略下有意义
     * @return 间隔时长，单位：秒
     */
    long interval() default 5;

    /**
     * 提示内容
     * @return 提示内容字符串
     */
    String message() default "幂等性冲突";

}
