package cn.com.mockingbird.robin.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池配置
 * <p>
 * 使用{@link Async}注解，在默认情况下用的是 SimpleAsyncTaskExecutor 线程池，该线程池不是真正意义上的线程池，
 * 该线程池不重用线程，每次调用都会新建一条线程，因此建议在使用{@link Async}异步框架时应该自定义线程池
 *
 * @author zhaopeng
 * @date 2023/10/13 0:47
 **/
@Slf4j
@EnableAsync
@AutoConfiguration
public class ThreadPoolConfiguration implements AsyncConfigurer {

    @Bean(name = "defaultThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor defaultTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        // 只有在缓冲队列满了之后才会申请超过核心线程数的线程
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(50);
        taskExecutor.setKeepAliveSeconds(180);
        taskExecutor.setThreadNamePrefix("default-task-");
        /*
         * 当线程池的任务缓存队列已满并且线程池中的线程数目达到 maximumPoolSize，如果还有任务到来就会采取任务拒绝策略
         * 通常有以下四种策略：
         * ThreadPoolExecutor.AbortPolicy：丢弃任务并抛出RejectedExecutionException异常。
         * ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。
         * ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
         * ThreadPoolExecutor.CallerRunsPolicy：重试添加当前的任务，自动重复调用 execute() 方法，直到成功
         */
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
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
        return (ex, method, params) -> log.error("默认线程池执行异步任务（方法-[{}]）发生未知错误：", method.getName(), ex);
    }
}
