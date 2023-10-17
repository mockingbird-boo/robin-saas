package cn.com.mockingbird.robin.web.trace;

import cn.com.mockingbird.robin.common.util.UUIDUtils;
import org.slf4j.MDC;

/**
 * MDC 日志追踪工具类
 *
 * @author zhaopeng
 * @date 2023/10/15 22:57
 **/
@SuppressWarnings("unused")
public class MDCUtils {

    /**
     * 追踪 KEY 的名称
     */
    public static final String TRACE_KEY = "traceId";

    /**
     * filter 的优先级，值越低越优先
     */
    public static final int FILTER_ORDER = -1;

    /**
     * 初始化 MDC
     */
    public static void trace() {
        MDC.put(TRACE_KEY, UUIDUtils.generateShortUuid());
    }

    public static void trace(String traceId) {
        MDC.put(TRACE_KEY, traceId);
    }

    public static String getTraceId() {
        return MDC.get(TRACE_KEY);
    }

    public static void endTrace() {
        MDC.remove(TRACE_KEY);
    }


}
