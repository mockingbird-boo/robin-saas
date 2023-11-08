package cn.com.mockingbird.robin.influxdb.influxql;

import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.List;

/**
 * InfluxQL 处理器接口
 *
 * @author zhaopeng
 * @date 2023/11/9 1:44
 **/
public interface StatementHandler {

    /**
     * 执行 InfluxQL
     * @param query {@link Query} 实例
     * @return InfluxQL 查询结果
     * @see QueryResult
     */
    QueryResult execute(Query query);

    <E> List<E> query(Query query, ResultHandler resultHandler);

}
