package cn.com.mockingbird.robin.web.async;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 一种线程池拒绝策略：阻塞等待
 * <p>
 * 能阻塞提交的任务，直到缓冲队列中释放出空间，或者发生等待超时抛出 RejectedExecutionException
 *
 * @author zhaopeng
 * @date 2023/11/19 19:41
 **/
@Slf4j
public class CallerBlocksPolicy implements RejectedExecutionHandler {

    private final int maxWaitSeconds;

    public CallerBlocksPolicy(int maxWaitSeconds) {
        this.maxWaitSeconds = maxWaitSeconds;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (!executor.isShutdown()) {
            try {
                BlockingQueue<Runnable> queue = executor.getQueue();
                if (log.isDebugEnabled()) {
                    log.debug("Attempting to queue task execution for " + this.maxWaitSeconds + " seconds");
                }
                if (!queue.offer(r, this.maxWaitSeconds, TimeUnit.SECONDS)) {
                    throw new RejectedExecutionException("Max wait time expired to queue task");
                }
                log.debug("Task execution queued");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RejectedExecutionException("Interrupted", e);
            }
        } else {
            throw new RejectedExecutionException("Executor has been shut down");
        }
    }
}
