package cn.com.mockingbird.robin.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * Request 工具类
 *
 * @author zhaopeng
 * @date 2023/10/19 22:37
 **/
public class RequestUtils {

    /**
     * 返回 HttpServletRequest 实例
     * @return HttpServletRequest 实例
     */
    public static HttpServletRequest getHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        return (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
    }

    /**
     * 返回客户端 ip 地址
     * @param request HttpServletRequest 实例
     * @return 客户端 ip 地址
     */
    public static String getClientIp(HttpServletRequest request) {
        return "";
    }

}
