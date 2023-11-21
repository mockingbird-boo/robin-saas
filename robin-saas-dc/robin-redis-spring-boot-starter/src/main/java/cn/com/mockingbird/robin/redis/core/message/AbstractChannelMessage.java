package cn.com.mockingbird.robin.redis.core.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 消息抽象类
 *
 * @author zhaopeng
 * @date 2023/11/21 13:07
 **/
public abstract class AbstractChannelMessage {

    /**
     * 获得 Redis Channel
     * JsonIgnore 避免序列化，Redis 发布 Channel 消息的时候，已经会指定
     *
     * @return Redis Channel
     */
    @JsonIgnore
    public abstract String getChannel();

}
