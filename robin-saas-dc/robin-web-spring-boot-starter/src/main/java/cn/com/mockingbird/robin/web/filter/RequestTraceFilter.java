package cn.com.mockingbird.robin.web.filter;

import cn.com.mockingbird.robin.common.util.BranchUtils;
import cn.com.mockingbird.robin.web.util.MDCUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Web 请求追踪过滤器
 *
 * @author zhaopeng
 * @date 2023/10/16 0:19
 **/
public class RequestTraceFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String traceId = request.getHeader(MDCUtils.TRACE_ID_HEADER_NAME);
            BranchUtils.isTureOrFalse(StringUtils.isBlank(traceId)).trueOrFalseHandle(MDCUtils::trace, () -> MDCUtils.trace(traceId));
            filterChain.doFilter(request, response);
        } finally {
            MDCUtils.endTrace();
        }
    }

}
