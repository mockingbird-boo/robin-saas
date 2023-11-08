package cn.com.mockingbird.robin.influxdb.influxql;

import org.influxdb.InfluxDB;
import org.influxdb.dto.QueryResult;

import java.util.List;

/**
 * 结果处理器
 *
 * @author zhaopeng
 * @date 2023/11/9 2:09
 **/
public interface ResultHandler {

    <R> List<R> handleResult(QueryResult queryResult, Class<R> clazz);

}
