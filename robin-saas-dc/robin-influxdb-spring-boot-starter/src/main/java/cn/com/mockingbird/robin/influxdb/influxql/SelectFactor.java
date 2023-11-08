package cn.com.mockingbird.robin.influxdb.influxql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * Influx Select 组成因子
 *
 * @author zhaopeng
 * @date 2023/11/9 1:18
 **/
@Getter
@Setter
public class SelectFactor extends Factor {

    /**
     * 查询字段
     */
    private String fields;

    /**
     * 当前页
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 分组
     */
    private String group;

    /**
     * 排序，influx db 只支持 time 排序
     */
    private String order;

    /**
     * 是否使用时区
     */
    private Boolean useTimeZone = false;

    /**
     * 默认时区
     */
    private String timeZone = "tz('Asia/Shanghai')";

    public String getFields() {
        return StringUtils.hasText(this.fields) ? this.fields : "*";
    }

    public String paginationClauses() {
        return "limit " + pageSize + " offset " + (pageNo - 1) * pageSize;
    }
}
