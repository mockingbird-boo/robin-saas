package cn.com.mockingbird.saas.router;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.http.server.ServerHttpRequest;

/**
 * 灰度负载均衡器
 * @author zhaopeng
 */
public interface GrayLoadBalancer {

    /**
     * 根据 serviceId 选择可用服务
     * @param serviceId 服务ID
     * @param request 当前请求
     * @return 服务实例
     */
    ServiceInstance choose(String serviceId, ServerHttpRequest request);

}
