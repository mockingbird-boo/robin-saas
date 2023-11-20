package cn.com.mockingbird.robin.api.autoconfigure;

import cn.com.mockingbird.robin.api.aspect.ApiSecurityAspect;
import cn.com.mockingbird.robin.api.aspect.ResponseDataSecurityAdvice;
import cn.com.mockingbird.robin.api.filter.RequestBodyTransferFilter;
import cn.com.mockingbird.robin.redis.autoconfigure.EnhancedRedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * API 安全接口自动配置类
 *
 * @author zhaopeng
 * @date 2023/11/2 23:45
 **/
@AutoConfigureAfter(EnhancedRedisAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.web.api.security", name = "enable", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(ApiSecurityProperties.class)
public class ApiSecurityAutoConfiguration {

    @Bean
    public RequestBodyTransferFilter requestBodyTransferFilter() {
        return new RequestBodyTransferFilter();
    }

    @Bean
    public ApiSecurityAspect apiSecurityAspect() {
        return new ApiSecurityAspect();
    }

    @Bean
    public ResponseDataSecurityAdvice responseDataSecurityAdvice() {
        return new ResponseDataSecurityAdvice();
    }

}
