package cn.com.mockingbird.robin.web.trace.log;

import cn.com.mockingbird.robin.common.constant.Standard;
import cn.com.mockingbird.robin.common.util.BranchUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

/**
 * 请求追踪器
 *
 * @author zhaopeng
 * @date 2023/11/20 0:32
 **/
@Slf4j
public class RequestTracker extends AbstractRequestLoggingFilter implements OrderedFilter {

    @Override
    protected void beforeRequest(@NonNull HttpServletRequest request, @NonNull String message) {
        String traceId = request.getHeader(Standard.RequestHeader.TRACE);
        BranchUtils.isTureOrFalse(StringUtils.isBlank(traceId))
                .trueOrFalseHandle(MDCUtils::trace, () -> MDCUtils.trace(traceId));
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    @Override
    protected void afterRequest(@NonNull HttpServletRequest request, @NonNull String message) {
        MDCUtils.endTrace();
        if (log.isDebugEnabled()) {
            log.debug(message);
        }
    }

    @Override
    public int getOrder() {
        return -1;
    }

}
