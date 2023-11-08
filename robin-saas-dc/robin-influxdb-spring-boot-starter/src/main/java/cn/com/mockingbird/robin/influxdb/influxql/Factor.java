package cn.com.mockingbird.robin.influxdb.influxql;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * InfluxQL 组成因子
 *
 * @author zhaopeng
 * @date 2023/11/8 22:54
 **/
@Getter
@Setter
@NoArgsConstructor
public abstract class Factor {

    /**
     * 表
     */
    private String measurement;

    /**
     * 条件
     */
    private String where;

    /**
     * 开始时间
     */
    private LocalDateTime start;

    /**
     * 结束时间
     */
    private LocalDateTime end;

    /**
     * 额外的 where 条件
     */
    private Map<String, Object> additionalParameters;


}
