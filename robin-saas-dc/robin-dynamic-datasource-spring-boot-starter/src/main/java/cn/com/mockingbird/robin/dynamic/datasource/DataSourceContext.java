package cn.com.mockingbird.robin.dynamic.datasource;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 数据源上下文
 *
 * @author zhaopeng
 * @date 2023/11/12 19:31
 **/
@SuppressWarnings("unused")
public class DataSourceContext {

    public static final ThreadLocal<String> DATA_SOURCE_KEY = new TransmittableThreadLocal<>();
    public static final String DEFAULT_DATA_SOURCE_KEY = "default";

    public static void setDataSource(String key) {
        DATA_SOURCE_KEY.set(key);
    }

    public static String dataSourceKey() {
        return DATA_SOURCE_KEY.get();
    }

    public static void clear() {
        DATA_SOURCE_KEY.remove();
    }

}
