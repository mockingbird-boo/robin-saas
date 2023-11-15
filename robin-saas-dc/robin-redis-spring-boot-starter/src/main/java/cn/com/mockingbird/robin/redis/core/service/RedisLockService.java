package cn.com.mockingbird.robin.redis.core.service;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redis 锁 服务工具
 * <p>
 * 底层主要用到了 redisson 组件
 *
 * @see RedissonClient
 * @author zhaopeng
 * @date 2023/10/19 1:28
 **/
@SuppressWarnings("unused")
public class RedisLockService {

    private final RedissonClient redissonClient;

    public RedisLockService(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    /**
     * 按名称返回 {@link RLock} 实例，非公平锁实例
     * @param name 参数名
     * @return {@link RLock} 实例
     */
    public RLock getLock(String name) {
        return redissonClient.getLock(name);
    }

    /**
     * 按名称返回 {@link RLock} 实例，公平锁实例
     * @param name 参数名
     * @return {@link RLock} 实例
     */
    public RLock getFairLock(String name) {
        return redissonClient.getFairLock(name);
    }

    /**
     * 按名称返回 {@link RReadWriteLock} 实例，读写锁实例
     * @param name 参数名
     * @return {@link RReadWriteLock} 实例
     */
    public RReadWriteLock getReadWriteLock(String name) {
        return redissonClient.getReadWriteLock(name);
    }

    /**
     * 获取锁，如果锁不可用，则当前线程处于休眠状态，直到获取锁为止
     * @param lock 锁实例
     */
    public void lock(RLock lock) {
        lock.lock();
    }

    /**
     * 获取锁，如果锁不可用，则当前线程处于休眠状态，
     * 直到获取锁继续执行或等待超过指定的超时时间后自动释放
     * @param lock 锁实例
     * @param timeout 超时时间
     * @param timeUnit 超时时间单位
     */
    public void lock(RLock lock, long timeout, TimeUnit timeUnit) {
        lock.lock(timeout, timeUnit);
    }

    /**
     * 获取锁，如果锁不可用，则当前线程处于休眠状态，
     * 直到获取锁继续执行或等待超过指定的超时时间后自动释放
     * @param lock 锁实例
     * @param timeout 超时时间，时间单位：秒
     */
    public void lock(RLock lock, long timeout) {
        lock.lock(timeout, TimeUnit.SECONDS);
    }

    /**
     * 按名称获取锁，并加锁执行一段逻辑返回处理结果
     * @param name 名称
     * @param supplier 执行逻辑
     * @return 返回结果
     * @param <T> 结果泛型
     */
    public <T> T lock(String name, Supplier<T> supplier) {
        RLock lock = redissonClient.getLock(name);
        try {
            lock.lock();
            return supplier.get();
        } finally {
            if (lock != null && lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    /**
     * 尝试获取锁
     * @param lock 锁实例
     * @param waitTime 最大等待时间，单位：秒
     * @param leaseTime 获取锁之后的释放锁时间，单位：秒，如果 <= 0，底层会启动看门狗
     * @return true - 锁成功获取；false - 失败
     */
    public boolean tryLock(RLock lock, long waitTime, long leaseTime) {
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试获取锁
     * @param lock 锁实例
     * @param waitTime 最大等待时间
     * @param leaseTime 获取锁之后的释放锁时间
     * @param timeUnit 时间单位
     * @return true - 锁成功获取；false - 失败
     */
    public boolean tryLock(RLock lock, long waitTime, long leaseTime, TimeUnit timeUnit) {
        try {
            return lock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 释放锁
     * @param lock 锁实例
     */
    public void unlock(RLock lock) {
        lock.unlock();
    }

}
