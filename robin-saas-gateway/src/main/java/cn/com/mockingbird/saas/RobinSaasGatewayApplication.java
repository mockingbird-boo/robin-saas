package cn.com.mockingbird.saas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 统一网关服务启动入口
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RobinSaasGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(RobinSaasGatewayApplication.class, args);
    }
}
