package cn.com.mockingbird.robin.web.config;

import cn.com.mockingbird.robin.web.async.CallerBlocksPolicy;
import cn.com.mockingbird.robin.web.async.ThreadPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 * <p>
 * 使用{@link Async}注解，在默认情况下用的是 SimpleAsyncTaskExecutor 线程池，该线程池不是真正意义上的线程池，
 * 该线程池不重用线程，每次调用都会新建一条线程，因此建议在使用{@link Async}异步框架时应该自定义线程池。
 *
 * @author zhaopeng
 * @date 2023/10/13 0:47
 **/
@Slf4j
@EnableAsync
@AutoConfiguration
@EnableConfigurationProperties(ThreadPoolProperties.class)
public class ThreadPoolConfiguration implements AsyncConfigurer {


    private final ThreadPoolProperties threadPoolProperties;

    public ThreadPoolConfiguration(ThreadPoolProperties threadPoolProperties) {
        this.threadPoolProperties = threadPoolProperties;
    }

    @Bean(name = "defaultThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor defaultTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(threadPoolProperties.getCorePoolSize());
        // 只有在缓冲队列满了之后才会申请超过核心线程数的线程
        taskExecutor.setMaxPoolSize(threadPoolProperties.getMaxPoolSize());
        taskExecutor.setQueueCapacity(threadPoolProperties.getQueueCapacity());
        taskExecutor.setKeepAliveSeconds(threadPoolProperties.getKeepAliveSeconds());
        taskExecutor.setThreadNamePrefix(threadPoolProperties.getThreadNamePrefix());
        taskExecutor.setRejectedExecutionHandler(getRejectedExecutionHandler(threadPoolProperties.getRejectPolicy()));
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * 指定默认的线程池
     * @return ThreadPoolTaskExecutor
     */
    @Override
    public Executor getAsyncExecutor() {
        return defaultTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error("默认线程池执行异步任务（方法-[{}]）发生异常：", method.getName(), ex);
    }

    private RejectedExecutionHandler getRejectedExecutionHandler(ThreadPoolProperties.RejectPolicy rejectPolicy) {
        RejectedExecutionHandler rejectedExecutionHandler = null;
        switch (rejectPolicy) {
            case ABORT -> rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();
            case DISCARD -> rejectedExecutionHandler = new ThreadPoolExecutor.DiscardPolicy();
            case DISCARD_OLDEST -> rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();
            case CALLER_RUNS -> rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
            case CALLER_BLOCKS -> rejectedExecutionHandler = new CallerBlocksPolicy(threadPoolProperties.getMaxWaitSeconds());
        }
        return rejectedExecutionHandler;
    }
}
