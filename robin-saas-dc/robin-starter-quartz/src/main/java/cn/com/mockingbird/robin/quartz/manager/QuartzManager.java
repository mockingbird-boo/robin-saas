package cn.com.mockingbird.robin.quartz.manager;

import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * Quartz Job 管理类
 *
 * @author zhaopeng
 * @date 2023/9/29 16:29
 **/
@Component
public class QuartzManager {

    private final Scheduler scheduler;

    public QuartzManager(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * Upsert 任务JOB
     * @param clazz JOB 类
     * @param name JOB 名称
     * @param groupName JOB 组名称
     * @param cron cron 表达式
     */
    public void upsertJob(Class<? extends QuartzJobBean> clazz, String name, String groupName, String cron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(name, groupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                addJob(clazz, name, groupName, cron);
            } else {
                if (trigger.getCronExpression().equals(cron)) {
                    return;
                }
                updateJob(name, groupName, cron);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新一个JOB
     * @param name JOB 名称
     * @param groupName JOB 组名称
     * @param cron cron 表达式
     */
    private void updateJob(String name, String groupName, String cron) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(name, groupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 新增一个JOB
     * @param clazz JOB 类
     * @param name JOB 名称
     * @param groupName JOB 组名称
     * @param cron cron 表达式
     */
    private void addJob(Class<? extends QuartzJobBean> clazz, String name, String groupName, String cron) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(name, groupName).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron)).startNow().build();
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 新增一个JOB
     * @param clazz JOB 类
     * @param name JOB 名称
     * @param groupName JOB 组名称
     * @param time 执行频率
     */
    public void addJob(Class<? extends Job> clazz, String name, String groupName, int time) {
        addJob(clazz, name, groupName, time, -1);
    }

    /**
     * 新增一个JOB
     * @param clazz JOB 类
     * @param name JOB 名称
     * @param groupName JOB 组名称
     * @param time 执行频率
     * @param times 重复次数
     */
    public void addJob(Class<? extends Job> clazz, String name, String groupName, int time, int times) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(name, groupName).build();
            Trigger trigger;
            if (times < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)
                        .withIntervalInSeconds(time))
                        .startNow()
                        .build();
            } else {
                trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)
                        .withIntervalInSeconds(time).withRepeatCount(times))
                        .startNow()
                        .build();
            }
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除一个 JOB
     * @param name JOB 名
     * @param groupName JOB 组名
     */
    public void deleteJob(String name, String groupName) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(name, groupName));
            scheduler.unscheduleJob(TriggerKey.triggerKey(name, groupName));
            scheduler.deleteJob(new JobKey(name, groupName));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停一个 JOB
     * @param name JOB 名
     * @param groupName JOB 组名
     */
    public void pauseJob(String name, String groupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(name, groupName));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复一个 JOB
     * @param name JOB 名
     * @param groupName JOB 组名
     */
    public void resumeJob(String name, String groupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(name, groupName));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 立即执行一个 JOB
     * @param name JOB 名
     * @param groupName JOB 组名
     */
    public void runJob(String name, String groupName) {
        try {
            scheduler.triggerJob(JobKey.jobKey(name, groupName));
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

}
