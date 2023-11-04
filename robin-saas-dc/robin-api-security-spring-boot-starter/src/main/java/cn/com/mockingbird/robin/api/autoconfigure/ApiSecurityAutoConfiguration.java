package cn.com.mockingbird.robin.api.autoconfigure;

import cn.com.mockingbird.robin.api.aspect.ApiSecurityAspect;
import cn.com.mockingbird.robin.api.filter.RequestBodyTransferFilter;
import cn.com.mockingbird.robin.redis.autoconfigure.EnhancedRedisAutoConfiguration;
import cn.com.mockingbird.robin.redis.core.service.RedisLockService;
import cn.com.mockingbird.robin.redis.core.service.RedisService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * API 安全接口自动配置类
 *
 * @author zhaopeng
 * @date 2023/11/2 23:45
 **/
@AutoConfiguration(after = EnhancedRedisAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.web.enhance.api.security", name = "enable", havingValue = "true")
@EnableConfigurationProperties(ApiSecurityProperties.class)
public class ApiSecurityAutoConfiguration {

    @Bean
    public RequestBodyTransferFilter requestBodyTransferFilter() {
        return new RequestBodyTransferFilter();
    }

    @Bean
    @ConditionalOnBean({ApiSecurityProperties.class, RedisService.class, RedisLockService.class})
    public ApiSecurityAspect apiSecurityAspect() {
        return new ApiSecurityAspect();
    }

}
