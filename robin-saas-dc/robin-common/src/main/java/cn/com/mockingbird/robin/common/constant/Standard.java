package cn.com.mockingbird.robin.common.constant;

/**
 * 标准接口
 *
 * @author zhaopeng
 * @date 2023/10/14 3:06
 **/
@SuppressWarnings("unused")
public interface Standard {

    /**
     * 日期时间格式标准
     */
    interface DateTimePattern {
        String DATE = "yyyy-MM-dd";
        String DATETIME = "yyyy-MM-dd HH:mm:ss";
        String MS_DATETIME = "yyyy-MM-dd HH:mm:ss.SSS";
    }

    /**
     * 字符串常量标准
     */
    interface Str {
        String EMPTY = "";
    }

    /**
     * 请求头标准
     */
    interface RequestHeader {

        String TRACE = "x-trace-id";

        String IDEMPOTENT_TOKEN = "x-idempotent-token";

    }
}
