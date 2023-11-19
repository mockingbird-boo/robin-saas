package cn.com.mockingbird.robin.web.async;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 线程池配置
 *
 * @author zhaopeng
 * @date 2023/11/19 17:51
 **/
@Data
@ConfigurationProperties("spring.default-thread-pool")
public class ThreadPoolProperties {

    /**
     * 核心线程数
     */
    private int corePoolSize = 2;

    /**
     * 最大线程数
     */
    private int maxPoolSize = 10;

    /**
     * 非核心线程空闲超时时间，单位：秒
     */
    private int keepAliveSeconds = 150;

    /**
     * 缓存队列大小
     */
    private int queueCapacity = 50;

    /**
     * 线程名前缀
     */
    private String threadNamePrefix = "default-task-";

    /**
     * 等待策略下最大等待时间，单位：秒
     */
    private int maxWaitSeconds = 2;

    /**
     * 拒绝策略
     */
    private RejectPolicy rejectPolicy = RejectPolicy.CALLER_BLOCKS;

    /**
     * 拒绝策略枚举
     */
    public enum RejectPolicy {
        /**
         * 丢弃任务并抛出 RejectedExecutionException 异常
         */
        ABORT,
        /**
         * 丢弃任务，但是不抛出异常
         */
        DISCARD,

        /**
         * 丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         */
        DISCARD_OLDEST,

        /**
         * 重试添加当前的任务，自动重复调用 execute() 方法，直到成功
         */
        CALLER_RUNS,

        /**
         * 阻塞等待，直到执行程序在其队列中具有空间，或者发生超时，超时将引发 RejectedExecutionException
         */
        CALLER_BLOCKS

    }

}
