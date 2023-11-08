package cn.com.mockingbird.robin.influxdb.mapper;

/**
 * Influx Mapper
 *
 * @author zhaopeng
 * @date 2023/11/9 0:14
 **/
public interface InfluxMapper {

    /**
     * 测试连接是否正常
     * @return true - 正常
     */
    Boolean ping();

    /**
     * 新建数据库
     * @param databases 数据库名
     */
    void newDatabase(String... databases);

}
