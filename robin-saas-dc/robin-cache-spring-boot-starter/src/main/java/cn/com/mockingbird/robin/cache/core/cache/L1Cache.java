package cn.com.mockingbird.robin.cache.core.cache;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;

/**
 * 采用 CaffeineCache 作为一级缓存（本地缓存）
 *
 * @author zhaopeng
 * @date 2023/11/21 23:34
 **/
public class L1Cache extends CaffeineCache {

    @SuppressWarnings("unchecked")
    public L1Cache(String name, Cache cache) {
        super(name, cache);
    }

}
