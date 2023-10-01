package cn.com.mockingbird.robin.quartz.schedule;

import cn.com.mockingbird.robin.quartz.entity.JobDetails;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.*;

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
            // 构建任务
            JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(name, groupName).build();
            // 任务触发器
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
    @SuppressWarnings("all")
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

    /**
     * 分页查询所有任务
     * @param pageNum 当前页
     * @param pageSize 页记录数
     * @return 分页任务列表
     */
    public PageInfo<JobDetails> selectAllJobs(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobDetails> jobs;
        try {
            GroupMatcher<JobKey> jobKeyGroupMatcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(jobKeyGroupMatcher);
            jobs = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    JobDetails jobDetails = new JobDetails();
                    if (trigger instanceof CronTrigger cronTrigger) {
                        jobDetails.setCron(cronTrigger.getCronExpression());
                        jobDetails.setTimeZone(cronTrigger.getTimeZone().getDisplayName());
                    }
                    jobDetails.setTriggerGroupName(trigger.getKey().getName());
                    jobDetails.setTriggerName(trigger.getKey().getGroup());
                    jobDetails.setJobGroupName(jobKey.getGroup());
                    jobDetails.setJobName(jobKey.getName());
                    jobDetails.setStartTime(trigger.getStartTime());
                    jobDetails.setClazz(scheduler.getJobDetail(jobKey).getJobClass().getName());
                    jobDetails.setNextFireTime(trigger.getNextFireTime());
                    jobDetails.setPreviousFireTime(trigger.getPreviousFireTime());
                    jobDetails.setStatus(scheduler.getTriggerState(trigger.getKey()).name());
                    jobs.add(jobDetails);
                }
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return new PageInfo<>(jobs);
    }

    /**
     * 查询所有的 JOB
     * @return 任务列表
     */
    @SuppressWarnings("all")
    public List<Map<String, Object>> selectAllJobs() {
        List<Map<String, Object>> jobs;
        try {
            GroupMatcher<JobKey> jobKeyGroupMatcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(jobKeyGroupMatcher);
            jobs = new ArrayList<>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    Map<String, Object> job = buildJob(jobKey, trigger, triggerState);
                    jobs.add(job);
                }
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return jobs;
    }

    /**
     * 查询所有正在运行的 JOB
     * @return 所有正在运行的 JOB 集合
     */
    @SuppressWarnings("all")
    public List<Map<String, Object>> selectRunningJobs() {
        List<Map<String, Object>> runningJobs;
        try {
            // 返回 JobExecutionContext 对象的列表，这些对象表示此计划程序实例中当前正在执行的所有作业
            List<JobExecutionContext> currentlyExecutingJobs = scheduler.getCurrentlyExecutingJobs();
            runningJobs = new ArrayList<>(currentlyExecutingJobs.size());
            for (JobExecutionContext currentlyExecutingJob : currentlyExecutingJobs) {
                JobDetail jobDetail = currentlyExecutingJob.getJobDetail();
                Trigger trigger = currentlyExecutingJob.getTrigger();
                JobKey key = jobDetail.getKey();
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                Map<String, Object> job = buildJob(key, trigger, triggerState);
                runningJobs.add(job);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        return runningJobs;
    }

    private Map<String, Object> buildJob(JobKey key, Trigger trigger, Trigger.TriggerState triggerState) {
        Map<String, Object> map = new HashMap<>(5);
        map.put("jobName", key.getName());
        map.put("jobGroupName", key.getGroup());
        map.put("description", "trigger:" + trigger.getKey());
        map.put("status", triggerState.name());
        if (trigger instanceof CronTrigger cronTrigger) {
            String cronExpression = cronTrigger.getCronExpression();
            map.put("jobTime", cronExpression);
        }
        return map;
    }

}
