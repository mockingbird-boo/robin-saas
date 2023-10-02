package cn.com.mockingbird.robin.quartz.service;

import cn.com.mockingbird.robin.quartz.common.StatusEnum;
import cn.com.mockingbird.robin.quartz.entity.Job;
import cn.com.mockingbird.robin.quartz.mapper.JobMapper;
import cn.com.mockingbird.robin.quartz.schedule.QuartzJobManager;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static cn.com.mockingbird.robin.quartz.entity.table.JobTableDef.JOB;

/**
 * 定时任务Job 业务逻辑处理类
 *
 * @author zhaopeng
 * @date 2023/10/1 19:21
 **/
@Slf4j
@Service
public class JobService {

    @Resource
    private JobMapper jobMapper;

    @Resource
    private QuartzJobManager quartzJobManager;

    @PostConstruct
    public void init() {
        log.info("The scheduled job is initializing.");
        jobMapper.selectListByQuery(QueryWrapper.create()
                .where(JOB.STATUS.in(StatusEnum.RUN.getStatus(), StatusEnum.STOP)))
            .forEach(item -> {
                TriggerKey triggerKey = quartzJobManager.getTriggerKey(item.getId());
                if (triggerKey == null) {
                    quartzJobManager.createJob(item);
                } else {
                    quartzJobManager.updateJob(item);
                }
            });
    }

    /**
     * 任务主键查询
     * @param id 任务ID
     * @return 任务实例
     */
    public Job selectById(Long id) {
        return jobMapper.selectOneById(id);
    }

    /**
     * 任务新增
     * @param job 任务实例
     * @return true - 任务新增成功，包括 Quartz 创建了一个定时器
     */
    @Transactional
    public boolean insert(Job job) {
        int rows = jobMapper.insert(job);
        if (rows == 1) {
            quartzJobManager.createJob(job);
        }
        return rows == 1;
    }

    /**
     * 任务更新
     * @param job 任务实例
     * @return true - 任务更新成功，包括 Quartz 更新了一个定时器
     */
    @Transactional
    public boolean update(Job job) {
        int rows = jobMapper.update(job);
        if (rows == 1) {
            quartzJobManager.updateJob(job);
        }
        return rows == 1;
    }

    /**
     * 任务暂停
     * @param id 任务ID
     */
    @Transactional
    public void pause(Long id) {
        Job job = jobMapper.selectOneById(id);
        if (job != null) {
            job.setStatus(StatusEnum.STOP.getStatus());
            if (jobMapper.update(job) == 1) {
                quartzJobManager.statusCheck(job);
            }
        }
    }

    /**
     * 任务恢复
     * @param id 任务ID
     */
    @Transactional
    public void resume(Long id) {
        Job job = jobMapper.selectOneById(id);
        if (job != null) {
            job.setStatus(StatusEnum.RUN.getStatus());
            if (jobMapper.update(job) == 1) {
                quartzJobManager.resumeJob(id);
            }
        }
    }

    /**
     * 任务执行
     * @param id 任务ID
     */
    public void run(Long id) {
        Job job = jobMapper.selectOneById(id);
        if (job != null && job.getStatus() != StatusEnum.DELETED.getStatus()) {
            quartzJobManager.runJob(job);
        }
    }

}
