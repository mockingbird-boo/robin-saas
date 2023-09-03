package cn.com.mockingbird.saas.router;

import lombok.Data;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.util.List;

/**
 * 网关路由
 *
 * @author zhaopeng
 * @date 2023/8/20 1:26
 **/
@Data
public class GatewayRoute {

    private static final long serialVersionUID = 1L;

    List<RouteDefinition> routes;

}
