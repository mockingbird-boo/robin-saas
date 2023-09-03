package cn.com.mockingbird.saas.service;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 安全规则业务接口
 *
 * @author zhaopeng
 * @date 2023/8/20 1:29
 **/
public interface SafeRuleService {

    /**
     * 过滤黑名单
     * @param exchange 交换机
     * @return Mono
     */
    Mono<Void> filterBlackList(ServerWebExchange exchange);

}
