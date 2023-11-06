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
        log.info("The cache is being removed. key - [{}], reason - [{}]", key, cause.name());
        // 超出最大缓存数量
        // if (cause == RemovalCause.SIZE) {}
    }
}
