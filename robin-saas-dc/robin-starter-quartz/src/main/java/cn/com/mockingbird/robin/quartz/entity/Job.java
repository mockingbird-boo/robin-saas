package cn.com.mockingbird.robin.quartz.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Job 实体
 *
 * @author zhaopeng
 * @date 2023/10/1 17:30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "定时任务JOB")
@Table("quartz_job")
public class Job implements Serializable {

    @Serial
    private static final long serialVersionUID = 3905324287359646312L;

    /**
     * 任务调度参数 KEY
     */
    public static final String JOB_PARAM_KEY = "BOOT_JOB_PARAM_KEY";

    @Id(keyType = KeyType.Auto)
    @Schema(description = "任务ID")
    private Long id;

    @Schema(description = "执行Bean")
    private String bean;

    @Schema(description = "执行参数")
    private String params;

    @Schema(description = "cron表达式")
    private String cron;

    @Schema(description = "任务状态：1-正常；2-暂停；3-删除")
    private Integer status;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}
