package cn.com.mockingbird.robin.redis.autoconfigure;

import cn.com.mockingbird.robin.redis.core.lock.DistributedLockAspect;
import cn.com.mockingbird.robin.redis.core.service.*;
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
@ConditionalOnBean(RedisTemplate.class)
public class EnhancedRedisAutoConfiguration {

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisService redisService(RedisTemplate<String, Object> redisTemplate) {
        RedisService redisService = new RedisService();
        redisService.setRedisTemplate(redisTemplate);
        return redisService;
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisStringService redisStringService(RedisTemplate<String, Object> redisTemplate) {
        RedisStringService redisStringService = new RedisStringService();
        redisStringService.setRedisTemplate(redisTemplate);
        return redisStringService;
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisHashService redisHashService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisHashService(redisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisListService redisListService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisListService(redisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisSetService redisSetService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisSetService(redisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    @SuppressWarnings("all")
    public RedisZSetService redisZSetService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisZSetService(redisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedisTemplate.class)
    public RedisStreamService redisStreamService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisStreamService(redisTemplate);
    }

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    public RedisLockService redisLockService(RedissonClient redissonClient) {
        return new RedisLockService(redissonClient);
    }

    @Bean
    @ConditionalOnBean(RedisLockService.class)
    public DistributedLockAspect distributedLockAspect() {
        return new DistributedLockAspect();
    }

}
