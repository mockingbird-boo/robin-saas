package cn.com.mockingbird.robin.quartz.service;

import cn.com.mockingbird.robin.quartz.entity.Log;
import cn.com.mockingbird.robin.quartz.mapper.LogMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 任务日志业务逻辑处理类
 *
 * @author zhaopeng
 * @date 2023/10/2 2:36
 **/
@Service
public class LogService {

    @Resource
    private LogMapper logMapper;

    public int insert(Log log) {
        return logMapper.insert(log);
    }

}
