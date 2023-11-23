package cn.com.mockingbird.robin.common.thread;

import com.alibaba.ttl.threadpool.TtlExecutors;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ExecutorService;

/**
 * TTL 线程池工具类
 *
 * @author zhaopeng
 * @date 2023/11/22 17:53
 **/
@SuppressWarnings("unused")
@UtilityClass
public class TtlThreadPoolUtils {

    /**
     * TTL 修饰线程池，修饰后的线程任务中可以获取父线程中 ttlThreadLocal 设置的变量
     * @param executorService 要修饰的线程池
     * @return 修饰后的线程池
     */
    public ExecutorService getTtlExecutorService(ExecutorService executorService) {
        return TtlExecutors.getTtlExecutorService(executorService);
    }

    /**
     * 新创建一个 TTL 线程池
     * @param name 线程名称
     * @return cn.com.mockingbird.robin.common.thread.TtlThreadPoolTaskExecutor 实例
     */
    public ExecutorService newTtlThreadPool(String name) {
        int processors = Runtime.getRuntime().availableProcessors();
        return new TtlThreadPoolTaskExecutor(
                processors * 2,
                processors * 20,
                processors * 200,
                name
        );
    }

}
