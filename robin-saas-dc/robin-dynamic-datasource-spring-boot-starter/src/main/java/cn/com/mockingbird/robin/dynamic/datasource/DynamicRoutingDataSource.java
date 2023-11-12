package cn.com.mockingbird.robin.dynamic.datasource;

import cn.com.mockingbird.robin.dynamic.datasource.model.DataSourceInfo;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态路由数据源
 *
 * @author zhaopeng
 * @date 2023/11/7 23:04
 **/
@SuppressWarnings("unused")
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * 当前数据源 KEY
     */
    private static final ThreadLocal<String> CURRENT_DATASOURCE_KEY = new ThreadLocal<>();
    /**
     * 缓存所有的 DataSource
     */
    private final Map<Object, Object> dataSources = new HashMap<>();
    /**
     * 缓存所有的 DataSourceInfo
     */
    private final Map<Object, DataSourceInfo> dataSourceInfos = new HashMap<>();
    /**
     * 默认的数据源
     */
    private final DataSource defaultDataSource;

    public DynamicRoutingDataSource(DataSource dataSource) {
        this.defaultDataSource = dataSource;
    }

    @Override
    public void afterPropertiesSet() {
        this.dataSources.put(DataSourceContext.DEFAULT_DATA_SOURCE_KEY, defaultDataSource);
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(dataSources);
        super.afterPropertiesSet();
    }

    /**
     * 确定当前使用的数据源的 Key
     * <p>
     * 持久层框架运行通常最终会执行到当前方法，以获取数据源，从而获取连接
     * @return 当前数据源的 Key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return CURRENT_DATASOURCE_KEY.get();
    }

    /**
     * 新增数据源
     * @param dataSourceInfo 数据源信息
     * @param overwrite 如果存在相同 Key 的数据源，是否进行覆盖
     * @return true - 新增成功
     */
    public synchronized boolean addDataSource(DataSourceInfo dataSourceInfo, Boolean overwrite) {
        if (!overwrite && dataSources.containsKey(dataSourceInfo.getKey())) {
            return false;
        }
        if (dataSources.containsKey(dataSourceInfo.getKey()) && dataSourceInfo.equals(dataSourceInfos.get(dataSourceInfo.getKey()))) {
            return true;
        }
        addDataSource(dataSourceInfo);
        return true;
    }

    /**
     * 新增并且切换数据源
     * @param dataSourceInfo 数据源信息
     * @param overwrite 如果存在相同 Key 的数据源，是否进行覆盖
     * @return true - 新增并且切换成功
     */
    public synchronized boolean addAndSwitchDataSource(DataSourceInfo dataSourceInfo, Boolean overwrite) {
        if (!overwrite && dataSources.containsKey(dataSourceInfo.getKey())) {
            return false;
        } else if (dataSources.containsKey(dataSourceInfo.getKey()) && dataSourceInfo.equals(dataSourceInfos.get(dataSourceInfo.getKey()))) {
            CURRENT_DATASOURCE_KEY.set(dataSourceInfo.getKey());
            return true;
        } else {
            addDataSource(dataSourceInfo);
            CURRENT_DATASOURCE_KEY.set(dataSourceInfo.getKey());
            return true;
        }
    }

    /**
     * 切换数据源
     * @param key 数据源 KEY
     * @return true - 切换成功
     */
    public synchronized boolean switchDataSource(String key) {
        if (!dataSources.containsKey(key)) {
            return false;
        }
        CURRENT_DATASOURCE_KEY.set(key);
        return true;
    }

    /**
     * 移除数据源，不允许移除当前正在使用的数据源
     * @param key 数据源 key
     * @return true - 数据源移除成功
     */
    public synchronized boolean removeDataSource(String key) {
        if (CURRENT_DATASOURCE_KEY.get().equals(key)) {
            return false;
        }
        dataSources.remove(key);
        dataSourceInfos.remove(key);
        return true;
    }

    /**
     * 切换成默认的数据源
     */
    public void switchDefaultDataSource() {
        CURRENT_DATASOURCE_KEY.set(DataSourceContext.DEFAULT_DATA_SOURCE_KEY);
    }

    /**
     * 获取默认的数据源
     * @return 默认的数据源
     */
    public DataSource getDefaultDataSource() {
        return (DataSource) dataSources.get(DataSourceContext.DEFAULT_DATA_SOURCE_KEY);
    }

    private void addDataSource(DataSourceInfo dataSourceInfo) {
        DataSource dataSource = newDataSource(dataSourceInfo);
        dataSources.put(dataSourceInfo.getKey(), dataSource);
        dataSourceInfos.put(dataSourceInfo.getKey(), dataSourceInfo);
        super.afterPropertiesSet();
    }

    private DataSource newDataSource(DataSourceInfo dataSourceInfo) {
        return DataSourceBuilder.create().type(HikariDataSource.class)
                .url(dataSourceInfo.getUrl())
                .driverClassName(dataSourceInfo.getDriverClassName())
                .username(dataSourceInfo.getUsername())
                .password(dataSourceInfo.getPassword())
                .build();
    }

}
