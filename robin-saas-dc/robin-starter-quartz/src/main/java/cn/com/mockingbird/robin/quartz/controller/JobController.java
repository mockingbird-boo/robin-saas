package cn.com.mockingbird.robin.quartz.controller;

import cn.com.mockingbird.robin.quartz.entity.Job;
import cn.com.mockingbird.robin.quartz.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * Quartz JOB Controller
 *
 * @author zhaopeng
 * @date 2023/10/2 17:56
 **/
@RestController
@RequestMapping("/api/quartz/job")
@Tag(name = "Quartz 定时任务相关接口")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @Operation(summary = "任务查询")
    @GetMapping("/{id}")
    public Job getJob(@PathVariable("id") Long id) {
        return jobService.selectById(id);
    }

    @Operation(summary = "任务新增")
    @PostMapping
    public Job addJob(@RequestBody Job job) {
        jobService.insert(job);
        return job;
    }

    @Operation(summary = "任务更新")
    @PutMapping
    public Job updateJob(@RequestBody Job job) {
        jobService.update(job);
        return job;
    }

    @Operation(summary = "任务停止")
    @PostMapping("/pause/{id}")
    public void pause(@PathVariable("id") Long id) {
        jobService.pause(id);
    }

    @Operation(summary = "任务恢复")
    @PostMapping("/resume/{id}")
    public void resume(@PathVariable("id") Long id) {
        jobService.resume(id);
    }

    @Operation(summary = "任务执行")
    @PostMapping("/run/{id}")
    public void run(@PathVariable("id") Long id) {
        jobService.run(id);
    }

}
