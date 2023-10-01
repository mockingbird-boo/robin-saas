package cn.com.mockingbird.robin.quartz.schedule;

import cn.com.mockingbird.robin.quartz.common.StatusEnum;
import cn.com.mockingbird.robin.quartz.entity.Job;
import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * Quartz 定时任务管理器
 *
 * @author zhaopeng
 * @date 2023/10/1 23:27
 **/
@Component
public class QuartzJobManager {

    private static final String JOB_KEY_PREFIX = "BOOT_JOB_";

    private final Scheduler scheduler;

    public QuartzJobManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 获取任务触发器KEY
     * @param jobId 任务ID
     * @return 任务触发器KEY
     */
    public TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_KEY_PREFIX + jobId);
    }

    /**
     * 获取定时任务KEY
     * @param jobId 任务ID
     * @return 任务触发器
     */
    public JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_KEY_PREFIX + jobId);
    }

    /**
     * 获取Cron表达式触发器
     * @param jobId 任务ID
     * @return Cron 表达式触发器
     */
    public CronTrigger getCronTrigger(Long jobId) {
        try {
            return (CronTrigger) this.scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建定时任务
     * @param job 任务实例
     */
    public void createJob(Job job) {
        try {
            // 1.构建任务详情JobDetail
            JobDetail jobDetail = JobBuilder.newJob(QuartzJobRecorder.class).withIdentity(getJobKey(job.getId())).build();
            // 2.构建任务执行触发器CronTrigger
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(getTriggerKey(job.getId()))
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()).withMisfireHandlingInstructionDoNothing())
                    .build();
            // 3.通过调度器进行任务调度
            scheduler.scheduleJob(jobDetail, cronTrigger);
            // 4.状态校验
            statusCheck(job);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新定时任务
     * @param job 任务实例
     */
    public void updateJob(Job job) {
        try {
            // 1.查询触发器KEY
            TriggerKey triggerKey = getTriggerKey(job.getId());
            // 2.更新触发器
            CronTrigger cronTrigger = getCronTrigger(job.getId())
                    .getTriggerBuilder()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron())
                            .withMisfireHandlingInstructionDoNothing())
                    .build();
            // 3.更新参数map
            cronTrigger.getJobDataMap().put(Job.JOB_PARAM_KEY, job);
            // 4.重新调度：会删除具有给定键的触发器，并保存新的触发器
            scheduler.rescheduleJob(triggerKey, cronTrigger);
            statusCheck(job);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复任务
     * @param jobId 任务ID
     */
    public void resumeJob(Long jobId) {
        try {
            this.scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除任务
     * @param jobId 任务ID
     */
    public void deleteJob(Long jobId) {
        try {
            this.scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行任务
     * @param job 任务实例
     */
    public void runJob(Job job) {
        try {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(Job.JOB_PARAM_KEY, job);
            this.scheduler.triggerJob(getJobKey(job.getId()), dataMap);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 任务状态检查
     * 非“运行”状态的任务进行暂停
     * @param job 任务实例
     */
    public void statusCheck(Job job) {
        try {
            if (job.getStatus() != StatusEnum.RUN.getStatus()) {
                this.scheduler.pauseJob(getJobKey(job.getId()));
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
