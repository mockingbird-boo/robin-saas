package cn.com.mockingbird.robin.integration.config;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.integration.zookeeper.config.CuratorFrameworkFactoryBean;
import org.springframework.integration.zookeeper.lock.ZookeeperLockRegistry;

/**
 * Integration 自动配置
 *
 * @author zhaopeng
 * @date 2023/10/21 2:04
 **/
@AutoConfiguration(before = IntegrationAutoConfiguration.class)
public class EnhancedIntegrationAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(RedisOperations.class)
    @ConditionalOnSingleCandidate(RedisConnectionFactory.class)
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        // 构造具有默认（60 秒）锁定过期时间的 RedisLockRegistry ，锁的键前缀：redis-lock
        return new RedisLockRegistry(redisConnectionFactory, "redis-lock");
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty("spring.zookeeper.connection")
    public CuratorFrameworkFactoryBean curatorFrameworkFactoryBean(@Value("${spring.zookeeper.connection}") String connectionString) {
        return new CuratorFrameworkFactoryBean(connectionString);
    }

    @Bean
    @ConditionalOnSingleCandidate(CuratorFramework.class)
    public ZookeeperLockRegistry zookeeperLockRegistry(CuratorFramework curatorFramework) {
        return new ZookeeperLockRegistry(curatorFramework, "/zookeeper-lock");
    }

}
