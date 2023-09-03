package cn.com.mockingbird.saas.filter;

import cn.com.mockingbird.saas.config.RequestProperties;
import cn.com.mockingbird.saas.util.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 请求前处理过滤器
 *
 * @author zhaopeng
 * @date 2023/8/23 1:30
 **/
@Slf4j
@Component
@RequiredArgsConstructor
public class PreRequestFilter implements GlobalFilter, Ordered {

    private final RequestProperties requestProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (requestProperties.isTrace()) {
            String traceId = UUIDUtils.generateShortUUID();
            // TODO 链路追踪处理
        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
