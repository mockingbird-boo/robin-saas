package cn.com.mockingbird.robin.iam.support.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP 请求工具类
 *
 * @author zhaopeng
 * @date 2023/12/5 22:26
 **/
@UtilityClass
public class HttpRequestUtils {

    /**
     * 从 HttpServletRequest 中获取请求参数
     * @param request 请求参数
     * @return MultiValueMap 封装的请求参数
     */
    public MultiValueMap<String, String> getParameters(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>(parameterMap.size());
        parameterMap.forEach((key, values) -> {
            for (String value : values) {
                parameters.add(key, value);
            }
        });
        return parameters;
    }

    /**
     * 从 HttpServletRequest 中获取请求头参数
     * @param request 请求实例
     * @return Map 封装的请求头参数
     */
    public Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> header = new HashMap<>(16);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String thisName = headerNames.nextElement();
            String thisValue = request.getHeader(thisName);
            header.put(thisName, thisValue);
        }
        return header;
    }

}
