package cn.com.mockingbird.saas.util;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.reactive.resource.ResourceUrlProvider;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * WebFlux 工具类
 *
 * @author zhaopeng
 * @date 2023/8/23 0:19
 **/
public class WebFluxUtils {
    /**
     * 路径匹配器
     */
    private static final PathMatcher pathMatcher = new AntPathMatcher();
    /**
     * 用于获取客户端用于访问静态资源的公共 URL 路径的中央组件
     */
    private static final ResourceUrlProvider resourceUrlProvider = new ResourceUrlProvider();

    public static PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public static ResourceUrlProvider getResourceUrlProvider() {
        return resourceUrlProvider;
    }

    /**
     * 判断一个资源是否是静态资源
     * @param uri 资源路径
     * @param exchange 交换机
     * @return true or false
     */
    public static Mono<Boolean> isStaticResource(String uri, ServerWebExchange exchange) {
        Mono<String> staticUri = getResourceUrlProvider().getForUriString(uri, exchange);
        return staticUri.hasElement();
    }

    /**
     * 判断路径是否与指定的路径模式匹配
     * @param patterns 路径模式数组
     * @param path 路径
     * @return true or false
     */
    public static boolean isMatchingPath(String[] patterns, String path) {
        return isMatchingPath(Arrays.asList(patterns), path);
    }

    /**
     * 判断路径是否与指定的路径模式匹配
     * @param patterns 路径模式集合
     * @param path 路径
     * @return true or false
     */
    public static boolean isMatchingPath(List<String> patterns, String path) {
        PathMatcher pathMatcher = getPathMatcher();
        for (String pattern : patterns) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

}
