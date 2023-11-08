package cn.com.mockingbird.robin.influxdb.autoconfigure;

import org.influxdb.InfluxDB;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.influx.InfluxDbAutoConfiguration;

/**
 * 增强的 InfluxDB 自动配置
 *
 * @author zhaopeng
 * @date 2023/11/8 18:29
 **/
@AutoConfiguration(after = InfluxDbAutoConfiguration.class)
@ConditionalOnBean(InfluxDB.class)
public class EnhancedInfluxDbAutoConfiguration {

}
