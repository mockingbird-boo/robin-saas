package cn.com.mockingbird.robin.cache.core.listener;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * 缓存删除或淘汰时的监听器
 *
 * @author zhaopeng
 * @date 2023/11/7 1:05
 **/
@Slf4j
public class CaffeineRemovalListener implements RemovalListener<String, Object> {
    @Override
    public void onRemoval(@Nullable String key, @Nullable Object value, RemovalCause cause) {
        if (log.isDebugEnabled()) {
            log.debug("The cache was removed. key - [{}], reason - [{}]", key, cause.name());
            // 超出最大缓存数量
            switch (cause) {
                case SIZE -> log.debug("The entry was evicted due to size constraints");
                case EXPIRED -> log.debug("The entry's expiration timestamp has passed");
                case EXPLICIT -> log.debug("A manual removal may also be performed through the key");
                case REPLACED -> log.debug("The entry itself was not actually removed, but its value was replaced");
                case COLLECTED -> log.debug("The entry was removed automatically because its key or value was gc");
            }
        }
    }
}
