package cn.com.mockingbird.robin.quartz.entity;

import lombok.Data;

import java.util.Date;

/**
 * 任务详情
 *
 * @author zhaopeng
 * @date 2023/9/29 16:23
 **/
@Data
public class JobDetails {
    /**
     * 任务类
     */
    private String clazz;
    /**
     * cron 表达式
     */
    private String cron;
    /**
     * 触发器组名
     */
    private String triggerGroupName;
    /**
     * 触发器名
     */
    private String triggerName;
    /**
     * 任务组名
     */
    private String jobGroupName;
    /**
     * 任务名
     */
    private String jobName;
    /**
     * 下一次执行时间
     */
    private Date nextFireTime;
    /**
     * 前一次执行时间
     */
    private Date previousFireTime;
    /**
     * 开启时间
     */
    private Date startTime;
    /**
     * 时区
     */
    private String timeZone;
    /**
     * 状态
     */
    private String status;
}
