package cn.com.mockingbird.robin.dynamic.datasource.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态路由数据源
 *
 * @author zhaopeng
 * @date 2023/11/7 23:04
 **/
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * 确定当前使用的数据源的 Key
     * @return 当前数据源的 Key
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return null;
    }
}
