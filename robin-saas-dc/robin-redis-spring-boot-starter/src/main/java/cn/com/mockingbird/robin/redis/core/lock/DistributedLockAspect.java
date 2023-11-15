package cn.com.mockingbird.robin.redis.core.lock;

import cn.com.mockingbird.robin.redis.core.service.RedisLockService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁的切面
 *
 * @author zhaopeng
 * @date 2023/11/15 2:26
 **/
@Slf4j
@Aspect
public class DistributedLockAspect {


    @Resource
    private RedisLockService redisLockService;

    @Around("@annotation(distributedLock)")
    public Object aroundTarget(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        String key = distributedLock.key();
        if (!StringUtils.hasText(key)) {
            throw new LockRuntimeException("Lock's key requires not empty.");
        }
        LockType lockType = distributedLock.lock();
        RLock lock = buildLock(key, lockType);

        long waitTime = distributedLock.waitTime();
        long leaseTime = distributedLock.leaseTime();
        TimeUnit unit = distributedLock.unit();

        log.debug("Lock's type - [{}]; Lock's wait time - [{}]; Lock's lease time - [{}]", lockType.name(), waitTime, leaseTime);
        boolean locked = false;
        try {
            if (waitTime > 0) {
                locked = redisLockService.tryLock(lock, waitTime, leaseTime, unit);
            } else {
                redisLockService.lock(lock, leaseTime, unit);
                locked = true;
            }
            if (!locked) {
                throw new LockRuntimeException("Failed to lock.");
            }
            return joinPoint.proceed();
        } catch (Throwable e) {
            log.error("An exception occurred when locking.", e);
            if (e instanceof LockRuntimeException) {
                throw (LockRuntimeException) e;
            }
            throw new LockRuntimeException("An exception occurred when locking.");
        } finally {
            if (locked) {
                redisLockService.unlock(lock);
            }
        }
    }

    /**
     * 构建锁实例
     * <p>
     * 默认构建的是可重入锁，可重入锁也是非公平锁，阻塞式等待。
     * leaseTime 为过期时间，leaseTime <= 0 代表不自动解锁，默认 30s 自动过期，但不用担心业务执行期间发生过期，
     * 看门狗机制会自动续期，即使不手动解锁，锁默认会在30s内自动过期，不会产生死锁问题。
     * <p>
     * 读写锁：保证一定能读到最新数据。
     * 修改期间，写锁是一个排他锁（互斥锁）。读锁是一个共享锁。 写锁没释放，读锁就必须等待。
     * 针对读数据时，只要当前正在修改数据，读数据就会等待写完成后，拿到最新数据。
     * 读 + 读 ：相当于无锁，并发读，只会在 Redis 中记录好，所有当前的读锁都会同时加锁成功。
     * 写 + 读 ：等待写锁释放，才能读到新数据（读被阻塞）。
     * 写 + 写 ：阻塞方式。
     * 读 + 写 ：有读锁。写也需要等待
     *
     * @param key 锁的 Key
     * @param lockType 锁的 类型
     * @return 锁实例
     */
    private RLock buildLock(String key, LockType lockType) {
        RLock lock;
        switch (lockType) {
            case FAIR -> lock = redisLockService.getFairLock(key);
            case READ -> {
                RReadWriteLock readWriteLock = redisLockService.getReadWriteLock(key);
                lock = readWriteLock.readLock();
            }
            case WRITE -> {
                RReadWriteLock readWriteLock = redisLockService.getReadWriteLock(key);
                lock = readWriteLock.writeLock();
            }
            default -> lock = redisLockService.getLock(key);
        }
        return lock;
    }

}
