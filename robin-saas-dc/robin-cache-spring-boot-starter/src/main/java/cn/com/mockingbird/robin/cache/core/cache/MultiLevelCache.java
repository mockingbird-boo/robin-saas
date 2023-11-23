package cn.com.mockingbird.robin.cache.core.cache;

import cn.com.mockingbird.robin.cache.autoconfigure.MultiLevelCacheProperties;
import cn.com.mockingbird.robin.cache.core.model.CacheMessage;
import cn.com.mockingbird.robin.common.thread.TtlThreadPoolUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * 多级缓存
 *
 * @author zhaopeng
 * @date 2023/11/21 23:26
 **/
@Slf4j
public class MultiLevelCache extends AbstractValueAdaptingCache {

    @Resource
    private MultiLevelCacheProperties cacheProperties;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final L1Cache l1Cache;
    private final L2Cache l2Cache;

    private final ExecutorService executorService = TtlThreadPoolUtils.newTtlThreadPool("cache-pool");

    public MultiLevelCache(boolean allowNullValues, L1Cache l1Cache, L2Cache l2Cache) {
        super(allowNullValues);
        this.l1Cache = l1Cache;
        this.l2Cache = l2Cache;
    }

    /**
     * 在底层存储中执行实际查找
     *
     * @param key 要返回存储值的键
     * @return 缓存值
     */
    @Nullable
    @Override
    public Object lookup(@NonNull Object key) {
        ValueWrapper value;
        if (cacheProperties.getEnableL1Cache()) {
            value = l1Cache.get(key);
            if (value != null) {
                log.debug("L1 cache, key:[{}], value:[{}]", key, value.get());
                return value.get();
            }
        }
        value = l2Cache.get(key);
        if (value != null) {
            log.debug("L2 cache, key:[{}], value:[{}]", key, value.get());
            // 异步将二级缓存写到一级缓存
            if (cacheProperties.getEnableL1Cache()) {
                ValueWrapper finalValue = value;
                executorService.execute(() -> l1Cache.put(key, finalValue));
            }
            return value.get();
        }
        return null;
    }

    @NonNull
    @Override
    public String getName() {
        return cacheProperties.getName();
    }

    @SuppressWarnings("all")
    @Override
    public Object getNativeCache() {
        // 返回底层本机缓存提供程序
        return null;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T get(@NonNull Object key, @Nullable final Callable<T> valueLoader) {
        Object value = lookup(key);
        return (T) value;
    }

    @Override
    public void put(@NonNull Object key, Object value) {
        l2Cache.put(key, value);
        if (cacheProperties.getEnableL1Cache()) {
            asyncPublish(key, value);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(@NonNull Object key, Object value) {
        ValueWrapper existingValue = l2Cache.putIfAbsent(key, value);
        if (cacheProperties.getEnableL1Cache()) {
            asyncPublish(key, value);
        }
        return existingValue;
    }

    @Override
    public void evict(@NonNull Object key) {
        l2Cache.evict(key);
        if (cacheProperties.getEnableL1Cache()) {
            asyncPublish(key, null);
        }
    }

    @Override
    public boolean evictIfPresent(@NonNull Object key) {
        return super.evictIfPresent(key);
    }

    @Override
    public void clear() {
        l2Cache.clear();
        if (cacheProperties.getEnableL1Cache()) {
            asyncPublish(null, null);
        }
    }

    /**
     * 异步发布消息处理 L1 上的缓存
     * @param key 缓存 key
     * @param value 缓存值
     */
    private void asyncPublish(Object key, Object value) {
        executorService.execute(() -> {
            CacheMessage cacheMessage = new CacheMessage();
            cacheMessage.setCacheName(cacheProperties.getName());
            cacheMessage.setKey(key);
            cacheMessage.setValue(value);
            redisTemplate.convertAndSend(cacheMessage.getChannel(), cacheMessage);
        });
    }

}
