package cn.com.mockingbird.robin.redis.core.lock;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁注解
 *
 * @author zhaopeng
 * @date 2023/11/15 2:03
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    @AliasFor("key")
    String value();

    /**
     * 锁的 key
     */
    @AliasFor("value")
    String key();

    /**
     * 锁的等待时长，单位：{@code unit}。
     * 如果该值的设置大于 0，默认会使用 tryLock 方法加锁，否则会使用阻塞锁
     */
    long waitTime() default 0L;

    /**
     * 锁的释放时间，单位：{@code unit}。
     * 如果该值的设置大于 0，底层不会自动启动开门狗，否则得到锁的线程保持执行直到显式解锁
     */
    long leaseTime() default -1L;

    /**
     * 时间参数的单位，默认是秒
     */
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 锁的类型，默认是可重入的
     */
    LockType lockType() default LockType.REENTRANT;

}
