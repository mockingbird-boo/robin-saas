package cn.com.mockingbird.robin.quartz.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 任务日志
 *
 * @author zhaopeng
 * @date 2023/10/1 18:52
 **/
@Data
@Table("quartz_log")
public class Log {

    @Id(keyType = KeyType.Auto)
    private Long id;

    private Long jobId;

    private String bean;

    private String params;

    private Integer status;

    private String error;

    private Integer times;

    private LocalDateTime createdTime;

}
