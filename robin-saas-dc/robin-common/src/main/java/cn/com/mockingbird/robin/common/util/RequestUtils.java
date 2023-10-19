package cn.com.mockingbird.robin.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
        String ip = request.getHeader("X-Forwarded-For");
        // 如果通过多级反向代理，X-Forwarded-For的值不止一个，而是一串用逗号分隔的IP值，此时取X-Forwarded-For中第一个非unknown的有效IP字符串
        if (isEffective(ip) && (ip.contains(","))) {
            String[] array = ip.split(",");
            for (String element : array) {
                if (isEffective(element)) {
                    ip = element;
                    break;
                }
            }
        }
        if (!isEffective(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (!isEffective(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!isEffective(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                // 根据网卡取本机配置的IP
                InetAddress inet;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    inet = null;
                }
                if (inet != null) {
                    ip = inet.getHostAddress();
                }
            }
        }
        return ip;
    }

    /**
     * 获取客户端源端口
     * @param request HttpServletRequest 实例
     * @return 端口
     */
    @SuppressWarnings("unused")
    public static Long getRemotePort(HttpServletRequest request){
        String port = request.getHeader("remote-port");
        if (port != null && !port.isEmpty()) {
            try {
                return Long.parseLong(port);
            } catch (NumberFormatException e) {
                return 0L;
            }
        } else {
            return 0L;
        }
    }

    /**
     * 检查 ip 是否有效
     * @param ip ip 地址
     * @return true - 有效；false - 无效
     */
    private static boolean isEffective(final String ip) {
        return (null != ip) && (!"".equals(ip.trim()))
                && (!"unknown".equalsIgnoreCase(ip.trim()));
    }

}
