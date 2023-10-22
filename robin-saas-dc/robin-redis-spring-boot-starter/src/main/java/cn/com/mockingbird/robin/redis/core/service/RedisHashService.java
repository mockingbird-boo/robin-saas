package cn.com.mockingbird.robin.redis.core.service;

import cn.com.mockingbird.robin.redis.core.util.SerialUtils;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Hash（哈希）数据类型服务工具
 * <p>
 * 适用于对象存储、购物车（Map<用户id,<商品id, 数量>>）等场景
 *
 * @author zhaopeng
 * @date 2023/10/22 15:39
 **/
@SuppressWarnings("unused")
public class RedisHashService {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisHashService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisHashService() {
    }

    /**
     * 返回 HashOperations 实例
     * @return HashOperations 实例 - Redis 映射处理哈希的特定操作实例
     */
    public HashOperations<String, String, Object> opsForHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * 设置键哈希
     * @param key 键 - 要求非空
     * @param hashKey 哈希键 - 要求非空
     * @param value 哈希值
     */
    public void put(final String key, final String hashKey, final Object value) {
        this.opsForHash().put(key, hashKey, value);
    }

    /**
     * 批量设置键哈希
     * @param key 键 - 要求非空
     * @param data Hash 数据 - 要求非空
     */
    public void put(final String key, final Map<String, Object> data) {
        this.opsForHash().putAll(key, data);
    }

    /**
     * 设置键哈希，当哈希中哈希键不存在时才设置成功
     * @param key 键 - 要求非空
     * @param hashKey 哈希键 - 要求非空
     * @param value 哈希值
     * @return true - 设置成功
     */
    public Boolean putIfAbsent(final String key, final String hashKey, final Object value) {
        return this.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除哈希
     * @param key 键 - 要求非空
     * @return true - 删除成功
     */
    public Boolean delete(final String key) {
        if (redisTemplate.type(key) == DataType.HASH) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    /**
     * 删除哈希中哈希键对应的数据
     * @param key 键 - 要求非空
     * @param hashKey 哈希键 - 要求非空
     */
    public void delete(final String key, final String hashKey) {
        this.opsForHash().delete(key, hashKey);
    }

    /**
     * 批量删除哈希中哈希键对应的数据
     * @param key 键 - 要求非空
     * @param hashKeys 哈希键数组
     */
    public void delete(final String key, final Object... hashKeys) {
        this.opsForHash().delete(key, hashKeys);
    }

    /**
     * 哈希值增量自增
     * @param key 键 - 要求非空
     * @param hashKey 哈希键 - 要求非空
     * @param delta 增量
     * @return 自增结果
     */
    public Long increment(final String key, final String hashKey, final long delta) {
        return this.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 哈希值增量自增
     * @param key 键 - 要求非空
     * @param hashKey 哈希键 - 要求非空
     * @param delta 增量
     * @return 自增结果
     */
    public Double increment(final String key, final String hashKey, double delta) {
        return this.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * 查询哈希值
     * @param key 键 - 要求非空
     * @param hashKey 哈希键 - 要求非空
     * @return 哈希值
     */
    public Object get(final String key, final String hashKey) {
        return this.opsForHash().get(key, hashKey);
    }

    /**
     * 返回哈希键集合
     * @param key 键 - 要求非空
     * @return 哈希键集合
     */
    public Set<String> keys(final String key) {
        return redisTemplate.<String, Object>opsForHash().keys(key);
    }

    /**
     * 返回哈希键的数量
     * @param key 键 - 要求非空
     * @return 哈希键的数量
     */
    public Long size(final String key) {
        return this.opsForHash().size(key);
    }

    /**
     * 返回哈希值集合
     * @param key 键 - 要求非空
     * @param hashKeys 哈希键集合 - 要求非空
     * @return 哈希值集合
     */
    public List<Object> multiGet(final String key, final Set<String> hashKeys) {
        return redisTemplate.<String, Object>opsForHash().multiGet(key, hashKeys);
    }

    /**
     * 返回指定键的哈希数据
     * @param key 键 - 要求非空
     * @return 哈希数据
     */
    public Map<String, Object> entries(final String key) {
        return redisTemplate.<String, Object>opsForHash().entries(key);
    }

    /**
     * 返回指定键的哈希数据
     * @param key 键 - 要求非空
     * @param redisSerializer 序列化器
     * @return 哈希数据
     */
    public Map<String, Object> entries(final String key, final RedisSerializer<Object> redisSerializer) {
        Assert.notNull(key, "Key requirements are not null");
        return redisTemplate.execute((RedisCallback<Map<String, Object>>) connection -> {
            Map<byte[], byte[]> serializedData = connection.hashCommands().hGetAll(key.getBytes());
            if (CollectionUtils.isEmpty(serializedData)) {
                return Collections.emptyMap();
            }
            Map<String, Object> data = new HashMap<>(serializedData.size());
            serializedData.forEach((k, v) -> data.put(SerialUtils.deserializeValue(k, RedisSerializer.string()), SerialUtils.deserializeValue(v, redisSerializer)));
            return data;
        });
    }

}
