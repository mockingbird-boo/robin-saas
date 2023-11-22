package cn.com.mockingbird.robin.common.thread;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.*;

/**
 * ttl 线程池
 *
 * @author zhaopeng
 * @date 2023/11/22 2:50
 **/
public class TtlThreadPoolTaskExecutor extends ThreadPoolExecutor {

    public TtlThreadPoolTaskExecutor(int corePoolSize, int maximumPoolSize, int queueCapacity, String name) {
        this(
                corePoolSize,
                maximumPoolSize,
                0L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                new ThreadFactoryBuilder().setNamePrefix(name).build(),
                new AbortPolicy()
        );
    }

    public TtlThreadPoolTaskExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    public void execute(Runnable command) {
        TtlRunnable ttlRunnable = TtlRunnable.get(command);
        super.execute(ttlRunnable);
    }

    @Override
    public Future<?> submit(Runnable task) {
        Runnable ttlRunnable = TtlRunnable.get(task);
        return super.submit(ttlRunnable);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        Runnable ttlRunnable = TtlRunnable.get(task);
        return super.submit(ttlRunnable, result);
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        Callable<T> ttlCallable = TtlCallable.get(task);
        return super.submit(ttlCallable);
    }
}
