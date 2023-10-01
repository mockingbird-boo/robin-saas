package cn.com.mockingbird.robin.quartz.controller;

import cn.com.mockingbird.robin.quartz.entity.JobDetails;
import cn.com.mockingbird.robin.quartz.schedule.QuartzManager;
import com.github.pagehelper.PageInfo;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.*;

/**
 * 任务 Cotroller
 *
 * @author zhaopeng
 * @date 2023/9/30 0:26
 **/
@RestController
@RequestMapping("/api/quartz/job")
public class JobController {

    private final QuartzManager quartzManager;

    public JobController(QuartzManager quartzManager) {
        this.quartzManager = quartzManager;
    }

    @PostMapping
    public void addJob(@RequestParam("className") String className,
                       @RequestParam("group") String group,
                       @RequestParam("cron") String cron) throws Exception {
        quartzManager.upsertJob(getClass(className), className, group, cron);
    }

    @PutMapping("/pause")
    public void pauseJob(@RequestParam("mame") String name, @RequestParam("group") String group) {
        quartzManager.pauseJob(name, group);
    }

    @PutMapping("/resume")
    public void resumeJob(@RequestParam("mame") String name, @RequestParam("group") String group) {
        quartzManager.resumeJob(name, group);
    }

    @PutMapping("/run")
    public void runJob(@RequestParam("mame") String name, @RequestParam("group") String group) {
        quartzManager.runJob(name, group);
    }

    @DeleteMapping
    public void deleteJob(@RequestParam("mame") String name, @RequestParam("group") String group) {
        quartzManager.deleteJob(name, group);
    }

    @GetMapping
    public PageInfo<JobDetails>  getJobs(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        return quartzManager.selectAllJobs(pageNum, pageSize);
    }

    @SuppressWarnings("unchecked")
    private Class<? extends QuartzJobBean> getClass(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return (Class<? extends QuartzJobBean>) clazz;
    }
}
