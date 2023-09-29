package cn.com.mockingbird.robin.quartz.entity;

import lombok.Data;

import java.util.Date;

/**
 * JobDetails：可执行的调度程序
 *
 * @author zhaopeng
 * @date 2023/9/29 16:23
 **/
@Data
public class JobDetails {
    private String cronExpression;
    private String jobClassName;
    private String triggerGroupName;
    private String triggerName;
    private String jobGroupName;
    private String jobName;
    private Date nextFireTime;
    private Date previousFireTime;
    private Date startTime;
    private String timeZone;
    private String status;
}
