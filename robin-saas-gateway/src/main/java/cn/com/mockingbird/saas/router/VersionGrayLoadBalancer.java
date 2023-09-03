package cn.com.mockingbird.saas.router;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * 基于客户端版本号的灰度负载均衡器
 * @author zhaopeng
 */
@Slf4j
@AllArgsConstructor
public class VersionGrayLoadBalancer implements GrayLoadBalancer {

    private static final String VERSION = "version";
    private DiscoveryClient discoveryClient;

    private static final RandomGenerator RANDOM_GENERATOR = RandomGeneratorFactory.getDefault().create();

    @Override
    public ServiceInstance choose(String serviceId, ServerHttpRequest request) {
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
        // 注册中心不存在实例，抛出异常
        if (CollectionUtils.isEmpty(instances)) {
            log.warn("There are no service instances available for the [{}]", serviceId);
            throw new NotFoundException("There are no service instances available for " + serviceId);
        }
        // 从请求头中获取版本号，版本号不存在就随机返回实例
        String version = request.getHeaders().getFirst(VERSION);
        if (StringUtils.isBlank(version)) {
            int randomInt = getRandomInt(instances.size() + 1);
            log.debug("There is no version number, an instance is randomly selected, and the random number is [{}]", randomInt);
            return instances.get(randomInt);
        }
        // 遍历所有实例，找到版本号匹配的实例即返回
        for (ServiceInstance instance : instances) {
            Map<String, String> metadata = instance.getMetadata();
            String targetVersion = metadata.get(VERSION);
            if (StringUtils.equalsAnyIgnoreCase(version, targetVersion)) {
                log.debug("Successfully found the corresponding instance. ServiceID-[{}], Version-[{}]", serviceId, version);
                return instance;
            }
        }
        return instances.get(getRandomInt(instances.size() + 1));
    }

    private static int getRandomInt(int bound) {
        Assert.isTrue(bound > 1, "bound must be greater than 1");
        return RANDOM_GENERATOR.nextInt(1, bound);
    }

}
