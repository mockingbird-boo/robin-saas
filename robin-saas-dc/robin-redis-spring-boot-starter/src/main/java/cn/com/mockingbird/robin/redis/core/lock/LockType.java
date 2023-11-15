package cn.com.mockingbird.robin.redis.core.lock;

/**
 * 锁类型枚举
 *
 * @author zhaopeng
 * @date 2023/11/15 0:53
 **/
public enum LockType {
    /**
     * 可重入锁，也是非公平锁
     */
    REENTRANT,
    /**
     * 公平锁
     */
    FAIR,
    /**
     * 读锁
     */
    READ,
    /**
     * 写锁
     */
    WRITE
}
