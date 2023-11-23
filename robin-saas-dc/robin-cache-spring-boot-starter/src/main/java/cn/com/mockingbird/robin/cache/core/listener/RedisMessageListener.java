package cn.com.mockingbird.robin.cache.core.listener;

import cn.com.mockingbird.robin.cache.core.cache.L1Cache;
import cn.com.mockingbird.robin.cache.core.model.CacheMessage;
import cn.com.mockingbird.robin.common.util.BranchUtils;
import cn.com.mockingbird.robin.redis.core.message.AbstractChannelMessageListener;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Redis 消息监听器
 *
 * @author zhaopeng
 * @date 2023/11/7 1:13
 **/
@Slf4j
public class RedisMessageListener extends AbstractChannelMessageListener<CacheMessage> {

    @Setter
    private L1Cache l1Cache;

    @Override
    public void onMessage(CacheMessage message) {
        if (log.isDebugEnabled()) {
            log.debug("Message heard:[{}]", message);
        }
        BranchUtils.isTureOrFalse(message.getKey() == null).trueOrFalseHandle(
                // 通过删除所有映射使 L1 缓存无效，期望所有条目对于后续查找立即不可见
                () -> l1Cache.invalidate(),
                // 如果 L1 缓存存在此键的映射，则将其删除
                () -> l1Cache.evict(message.getKey())
        );
    }

}
