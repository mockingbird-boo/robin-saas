package cn.com.mockingbird.robin.cache.core.model;

import lombok.Data;

/**
 * 缓存消息
 *
 * @author zhaopeng
 * @date 2023/11/7 1:16
 **/
@Data
public class CacheMessage {

    private String cacheName;
    private String key;
    private Object value;
    private Integer type;

}
