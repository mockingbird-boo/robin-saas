package cn.com.mockingbird.robin.iam.support.web.servlet;

import cn.com.mockingbird.robin.iam.support.web.useragent.UserAgent;
import cn.com.mockingbird.robin.iam.support.web.useragent.UserAgentParser;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Servlet 上下文工具类
 *
 * @author zhaopeng
 * @date 2023/12/6 13:02
 **/
@SuppressWarnings("unused")
public final class ServletContextHelp {

    private static final List<MediaType> MEDIA_TYPES_ALL;

    static {
        // 返回仅包含指定对象的不可变列
        MEDIA_TYPES_ALL = Collections.singletonList(MediaType.ALL);
    }

    public ServletContextHelp() {
    }

    /**
     * 返回当前请求实例
     * @return HttpServletRequest 实例
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 返回当前响应实例
     * @return HttpServletResponse 实例
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes)Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 返回 HttpSession 实例，如果该请求没有会话，则创建一个会话
     * @return HttpSession 实例
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 返回 HttpSession 实例
     * @param create true 则在必要时为此请求创建一个新会话； false 如果没有当前会话则返回 nul
     * @return HttpSession 实例
     */
    public static HttpSession getSession(boolean create) {
        return getRequest().getSession(create);
    }

    /**
     * 会话设置属性
     * @param name 属性名
     * @param value 属性值
     */
    public static void setAttribute(String name, Object value) {
        getSession().setAttribute(name, value);
    }

    /**
     * 根据属性名获取会话属性值
     * @param name 属性
     * @return 属性值
     */
    public static Object getAttribute(String name) {
        return getSession().getAttribute(name);
    }

    /**
     * 根据属性名移除会话属性
     * @param name 属性名
     */
    public static void removeAttribute(String name) {
        getSession().removeAttribute(name);
    }

    /**
     * 根据请求参数名获取参数值
     * @param name 请求参数名
     * @return 参数值
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 将请求参数设置到目标对象中
     * @param request 请求实例
     * @param target 目标对象
     * @return 目标对象
     */
    public static Object getParameterObject(HttpServletRequest request, Object target) {
        Map<String, String> map = getParameterMap(request);
        // 获取给定目标对象的 BeanWrapper，访问 JavaBeans 风格的属性
        BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(target);
        // 如果为 true，则将使用默认对象值填充并遍历空路径位置，而不是导致 NullValueInNestedPathException
        wrapper.setAutoGrowNestedPaths(true);
        wrapper.setPropertyValues(map);
        return wrapper.getWrappedInstance();
    }

    /**
     * 从请求中获取请求参数并封装成 Map
     * @param request 请求实例
     * @return 请求参数 Map
     */
    public static Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>(16);
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (!paramValue.isEmpty()) {
                    map.put(paramName, paramValue);
                }
            }
        }
        return map;
    }

    /**
     * 根据 Cookie 名获取 Cookie
     * @param request 请求实例
     * @param name Cookie 名
     * @return Cookie 实例，null - 不存在
     */
    public static Cookie readCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCookieAll(request);
        return cookieMap.getOrDefault(name, null);
    }

    /**
     * 从 HttpServletRequest 中解析 Cookie 并封装成 Map
     * @param request 请求实例
     * @return 存放请求 Cookie 的 Map 实例
     */
    public static Map<String, Cookie> readCookieAll(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<>(16);
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 设置 Cookie
     * @param response 响应实例
     * @param name Cookie 名
     * @param value Cookie 值
     * @param path Cookie 路径
     * @param time Cookie 的最长期限（以秒为单位）
     * @return 响应实例
     */
    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, String path, int time) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        URLEncoder.encode(value, StandardCharsets.UTF_8);
        cookie.setMaxAge(time);
        response.addCookie(cookie);
        return response;
    }

    /**
     * 根据请求返回 Root Path
     * @param request 请求实例
     * @return Root Path
     */
    public static String getServerRootPath(HttpServletRequest request) {
        String scheme = request.getScheme();
        return scheme + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    }

    /**
     * 根据请求判断客户端类型是否是 “Mobile Phone”
     * @param request 请求实例
     * @return true - 是
     */
    public static boolean deviceTypeIsMobilePhone(HttpServletRequest request) {
        UserAgent userAgent = UserAgentParser.getUserAgent(request);
        return "Mobile Phone".equals(userAgent.getDeviceType());
    }

    /**
     * 根据请求获取 MimeType 的子类
     * @param request 请求实例
     * @return MimeType 子类集合
     */
    public static List<MediaType> getAcceptedMediaTypes(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        return StringUtils.hasText(acceptHeader) ? MediaType.parseMediaTypes(acceptHeader) : MEDIA_TYPES_ALL;
    }

    /**
     * 判断请求 Accept 是否为 “text/html”
     * @param request 请求实例
     * @return true - 是
     */
    public static boolean isHtmlRequest(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        return acceptHeader != null && acceptHeader.contains("text/html");
    }

    /**
     * 从请求中获取请求参数
     * @param request 请求实例
     * @return JSONObject 封装的请求参数
     * @throws IOException IO 异常
     */
    public static JSONObject getRequestJsonObject(HttpServletRequest request) throws IOException {
        String json = getRequestJsonString(request);
        return JSON.parseObject(json);
    }

    /**
     * 从请求中获取请求参数
     * @param request 请求实例
     * @return 请求参数
     * @throws IOException IO 异常
     */
    public static String getRequestJsonString(HttpServletRequest request) throws IOException {
        String submitMethod = request.getMethod();
        return HttpMethod.GET.name().equals(submitMethod) ?
                (new String(request.getQueryString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8)).replaceAll("%22", "\"")
                : getRequestPostStr(request);
    }

    /**
     * 请求正文转字节数组
     * @param request 请求实例
     * @return 请求正文字节数组
     * @throws IOException IO 异常
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request) throws IOException {
        // 返回请求正文的长度（以字节为单位）并由输入流提供，如果长度未知，则返回 -1
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        } else {
            byte[] buffer = new byte[contentLength];
            int read;
            for(int i = 0; i < contentLength; i += read) {
                read = request.getInputStream().read(buffer, i, contentLength - i);
                if (read == -1) {
                    break;
                }
            }
            return buffer;
        }
    }

    /**
     * 获取 POST 请求的请求参数
     * @param request 请求实例
     * @return POST 请求参数
     * @throws IOException IO 异常
     */
    public static String getRequestPostStr(HttpServletRequest request) throws IOException {
        byte[] buffer = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }

        return new String(buffer != null ? buffer : new byte[0], charEncoding);
    }

    /**
     * 从 URL 中获取请求参数
     * @param url 请求路径
     * @return Map 封装的请求参数
     */
    public static Map<String, String> getParameterForUrl(String url) {
        Map<String, String> map = new HashMap<>(16);
        url = URLDecoder.decode(url, StandardCharsets.UTF_8);
        if (url.indexOf(63) != -1) {
            String contents = url.substring(url.indexOf(63) + 1);
            String[] keyValues = contents.split("&");
            for (String keyValue : keyValues) {
                String key = keyValue.substring(0, keyValue.indexOf("="));
                String value = keyValue.substring(keyValue.indexOf("=") + 1);
                map.put(key, value);
            }
        }
        return map;
    }

}
