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
     * 实现策略：锁和令牌
     */
    enum Strategy {
        /**
         * 锁，会将“用户名 + 客户端IP + 类名 + 方法名”作为锁的 key
         */
        LOCK,
        /**
         * 令牌
         */
        TOKEN
    }

    /**
     * 策略，默认策略为锁策略
     * @return 策略类型
     */
    Strategy strategy() default Strategy.LOCK;

    /**
     * 间隔，锁策略下有意义，不应该超过1天
     * @return 间隔时长，单位：秒
     */
    long interval() default 5;

    /**
     * 提示内容
     * @return 提示内容字符串
     */
    String message() default "幂等性冲突";

}
