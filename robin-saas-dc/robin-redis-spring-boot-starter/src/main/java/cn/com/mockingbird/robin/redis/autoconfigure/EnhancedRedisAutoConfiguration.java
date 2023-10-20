package cn.com.mockingbird.robin.redis.autoconfigure;

import cn.com.mockingbird.robin.redis.core.service.RedisLockService;
import cn.com.mockingbird.robin.redis.core.service.RedisService;
import org.redisson.api.RedissonClient;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Redis 自动配置类
 *
 * @author zhaopeng
 * @date 2023/10/13 22:28
 **/
@AutoConfiguration(after = RedissonAutoConfiguration.class)
public class EnhancedRedisAutoConfiguration {

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisService redisService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisService(redisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    public RedisLockService redisLockService(RedissonClient redissonClient) {
        return new RedisLockService(redissonClient);
    }

}
