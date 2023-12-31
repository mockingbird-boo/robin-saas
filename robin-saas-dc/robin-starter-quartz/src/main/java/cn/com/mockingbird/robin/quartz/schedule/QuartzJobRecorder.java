package cn.com.mockingbird.robin.quartz.schedule;

import cn.com.mockingbird.robin.common.util.bean.OrikaUtils;
import cn.com.mockingbird.robin.quartz.common.ResultEnum;
import cn.com.mockingbird.robin.quartz.entity.Job;
import cn.com.mockingbird.robin.quartz.entity.Log;
import cn.com.mockingbird.robin.quartz.service.LogService;
import cn.com.mockingbird.robin.web.context.SpringApplicationContext;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

/**
 * 任务执行记录器
 * <p>
 * 通过反射执行目标bean的目标方法run方法同时，会进行执行日志记录
 *
 * @author zhaopeng
 * @date 2023/10/2 0:03
 **/
public class QuartzJobRecorder extends QuartzJobBean {

    private static final String TARGET_METHOD = "run";

    /**
     * 实际作业任务执行入口
     * @param context Quartz Job 执行上下文
     */
    @Override
    protected void executeInternal(JobExecutionContext context) {
        Job job = (Job) context.getMergedJobDataMap().get(Job.JOB_PARAM_KEY);
        LogService logService = SpringApplicationContext.getBean("logService", LogService.class);
        Log log = OrikaUtils.INSTANCE.convert(job, Log.class);
        log.setJobId(job.getId());
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            Object targetBean = SpringApplicationContext.getBean(job.getBean());
            Method targetMethod = targetBean.getClass().getMethod(TARGET_METHOD, String.class);
            targetMethod.setAccessible(true);
            targetMethod.invoke(targetBean, job.getParams());
            stopWatch.stop();
            log.setDuration((int)stopWatch.getTotalTimeMillis());
            log.setResult(ResultEnum.SUCCESS.getResult());
        } catch (Exception e) {
            stopWatch.stop();
            log.setDuration((int)stopWatch.getTotalTimeMillis());
            log.setResult(ResultEnum.FAILURE.getResult());
            log.setError(e.getMessage());
        } finally {
            logService.insert(log);
        }
    }
}
