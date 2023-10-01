package cn.com.mockingbird.robin.quartz.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 任务记录器
 *
 * @author zhaopeng
 * @date 2023/10/2 0:03
 **/
public class QuartzJobRecorder extends QuartzJobBean {
    /**
     * 实际作业任务执行入口
     * @param context Quartz Job 执行上下文
     * @throws JobExecutionException 任务执行异常
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // TODO 记录任务执行日志
    }
}
