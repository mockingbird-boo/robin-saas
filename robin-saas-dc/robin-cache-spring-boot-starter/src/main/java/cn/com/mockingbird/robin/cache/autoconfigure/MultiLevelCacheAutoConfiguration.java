package cn.com.mockingbird.robin.cache.autoconfigure;

import cn.com.mockingbird.robin.cache.core.cache.L1Cache;
import cn.com.mockingbird.robin.cache.core.cache.L2Cache;
import cn.com.mockingbird.robin.cache.core.cache.MultiLevelCache;
import cn.com.mockingbird.robin.cache.core.listener.CaffeineRemovalListener;
import cn.com.mockingbird.robin.cache.core.listener.RedisMessageListener;
import cn.com.mockingbird.robin.common.thread.TtlThreadPoolUtils;
import cn.com.mockingbird.robin.redis.core.util.SerialUtils;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * 缓存自动配置
 *
 * @author zhaopeng
 * @date 2023/11/21 20:10
 **/
@Configuration
@EnableConfigurationProperties(MultiLevelCacheProperties.class)
public class MultiLevelCacheAutoConfiguration {

    @Resource
    private MultiLevelCacheProperties cacheProperties;

    @Bean
    @ConditionalOnClass(CaffeineCache.class)
    @ConditionalOnProperty(prefix = "spring.multi-level-cache", name = "enableL1Cache", havingValue = "true", matchIfMissing = true)
    public L1Cache l1Cache() {
        int maxCapacity = (int) (Runtime.getRuntime().totalMemory() * cacheProperties.getMaxCapacityMemoryRatio());
        int initCapacity = (int) (maxCapacity * cacheProperties.getInitCapacityRatio());
        return new L1Cache(cacheProperties.getL1Name(), Caffeine.newBuilder()
                .initialCapacity(initCapacity)
                .maximumSize(maxCapacity)
                // 指定运行异步任务时要使用的执行器。当发送删除通知时、当由 AsyncCache 或 LoadingCache.refresh，
                // 或 refreshAfterWrite 执行异步计算时，或者当执行定期维护时，会委托该执行器去执行。
                .executor(TtlThreadPoolUtils.newTtlThreadPool("l1-cache-pool"))
                // Caffeine 缓存不会当值过期后立即执行清除，而是在写入或者读取操作之后执行少量维护工作，
                // 如果写入或者读取频率很高，那么不用担心，如果写入/读取频率较低，那么可以通过Cache.cleanUp()或者加scheduler去定时执行清除操作
                .scheduler(Scheduler.systemScheduler())
                // 设置缓存数据移除监听器
                .removalListener(new CaffeineRemovalListener())
                // 指定在缓存数据创建、最近一次替换其值或最后一次访问后经过固定持续时间后，应自动从缓存中删除
                .expireAfterAccess(Duration.of(cacheProperties.getL1TimeToLive(), ChronoUnit.SECONDS))
                // 开启 metrics 监控
                .recordStats()
                .build());
    }

    @Bean
    @ConditionalOnClass(RedisCache.class)
    public L2Cache l2Cache(RedisConnectionFactory connectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration.entryTtl(Duration.of(cacheProperties.getL2TimeToLive(), ChronoUnit.SECONDS));
        redisCacheConfiguration.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));
        redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(SerialUtils.genericJackson2JsonRedisSerializer()));
        return new L2Cache(cacheProperties.getL2Name(), redisCacheWriter, redisCacheConfiguration);
    }

    @Bean
    @ConditionalOnClass({L1Cache.class, L2Cache.class})
    public MultiLevelCache multiLevelCache(L1Cache l1Cache, L2Cache l2Cache) {
        return new MultiLevelCache(true, l1Cache, l2Cache);
    }

    @Bean
    @ConditionalOnBean(L1Cache.class)
    public RedisMessageListener redisMessageListener(L1Cache l1Cache) {
        RedisMessageListener redisMessageListener = new RedisMessageListener();
        redisMessageListener.setL1Cache(l1Cache);
        return redisMessageListener;
    }

}
