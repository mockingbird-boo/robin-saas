package cn.com.mockingbird.robin.cache.core.model;

import cn.com.mockingbird.robin.redis.core.message.AbstractChannelMessage;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 缓存消息
 *
 * @author zhaopeng
 * @date 2023/11/7 1:16
 **/
@Getter
@Setter
public class CacheMessage extends AbstractChannelMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = -1014348076987378854L;

    private String cacheName;
    private Object key;
    private Object value;
    private Integer type;

    private static final String DEFAULT_TOPIC = "multi-level-cache-topic";

    @Override
    public String getChannel() {
        return DEFAULT_TOPIC;
    }
}
