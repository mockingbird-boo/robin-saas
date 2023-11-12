package cn.com.mockingbird.robin.dynamic.datasource.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据源工具类
 *
 * @author zhaopeng
 * @date 2023/11/12 23:30
 **/
@SuppressWarnings("unused")
@Slf4j
@UtilityClass
public class DataSourceUtils {

    /**
     * 数据源是否可以获取连接
     * @param dataSource 数据源实例
     * @return true - 可以
     */
    public boolean isAvailable(DataSource dataSource) {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            connection.close();
            return true;
        } catch (SQLException e) {
            log.error("Unavailable data sources.", e);
            return false;
        }
    }

}
