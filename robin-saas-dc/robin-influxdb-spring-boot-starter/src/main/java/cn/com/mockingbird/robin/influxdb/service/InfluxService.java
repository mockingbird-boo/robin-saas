package cn.com.mockingbird.robin.influxdb.service;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Influxdb 服务工具
 *
 * @author zhaopeng
 * @date 2023/11/8 18:21
 **/
public class InfluxService {

    private final InfluxDB influxDb;

    public InfluxService(InfluxDB influxDb) {
        this.influxDb = influxDb;
    }

    public void insert(String measurement, Map<String, String> tags, Map<String, Object> fields) {
        Point.Builder builder = Point.measurement(measurement);
        builder.time(Instant.now().toEpochMilli(), TimeUnit.MILLISECONDS);
        builder.tag(tags);
        builder.fields(fields);
        influxDb.write(builder.build());
    }


}
