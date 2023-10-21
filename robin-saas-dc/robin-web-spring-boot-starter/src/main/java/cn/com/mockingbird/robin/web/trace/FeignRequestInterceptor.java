package cn.com.mockingbird.robin.web.trace;

import cn.com.mockingbird.robin.common.constant.Standard;
import cn.com.mockingbird.robin.common.util.request.RequestUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Enumeration;

/**
 * Feign 请求拦截器
 *
 * @author zhaopeng
 * @date 2023/10/22 2:37
 **/
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = RequestUtils.getHttpRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        // header 透传
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            // 忽略 “Content-Length”
            if (!StringUtils.equalsIgnoreCase(Standard.RequestHeader.CONTENT_LENGTH, headerName)) {
                String headerValue = request.getHeader(headerName);
                requestTemplate.header(headerName, headerValue);
            }
        }
        // 传递 x-trace-id
        String traceId = MDCUtils.getTraceId();
        if (StringUtils.isNotBlank(traceId)) {
            requestTemplate.header(Standard.RequestHeader.TRACE, traceId);
        }
    }

}
