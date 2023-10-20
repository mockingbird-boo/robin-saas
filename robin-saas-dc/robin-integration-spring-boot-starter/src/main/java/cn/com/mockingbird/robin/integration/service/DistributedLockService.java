package cn.com.mockingbird.robin.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 分布式锁服务类
 *
 * @author zhaopeng
 * @date 2023/10/21 2:18
 **/
@SuppressWarnings("unused")
@Slf4j
public abstract class DistributedLockService {
    public abstract Lock getLock(String lockKey);

    public boolean tryLock(Lock lock, long time, TimeUnit timeUnit) {
        try {
            return lock.tryLock(time, timeUnit);
        } catch (InterruptedException e) {
            log.error("An exception occurs during the locking process.", e);
            return false;
        }
    }

    /**
     * 释放锁
     * @param lock 锁实例
     */
    public void unlock(Lock lock) {
        lock.unlock();
    }

    public static class RedisDistributedLockService extends DistributedLockService {

        private final RedisLockRegistry redisLockRegistry;

        public RedisDistributedLockService(RedisLockRegistry redisLockRegistry) {
            this.redisLockRegistry = redisLockRegistry;
        }

        @Override
        public Lock getLock(String lockKey) {
            return redisLockRegistry.obtain(lockKey);
        }

    }

    public static class ZookeeperDistributedLockService extends DistributedLockService {

        private final ZookeeperLockRegistry zookeeperLockRegistry;

        public ZookeeperDistributedLockService(ZookeeperLockRegistry zookeeperLockRegistry) {
            this.zookeeperLockRegistry = zookeeperLockRegistry;
        }

        @Override
        public Lock getLock(String lockKey) {
            return zookeeperLockRegistry.obtain(lockKey);
        }

    }

}
