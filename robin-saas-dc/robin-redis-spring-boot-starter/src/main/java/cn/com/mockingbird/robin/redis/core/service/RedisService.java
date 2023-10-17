package cn.com.mockingbird.robin.redis.core.service;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 服务工具
 *
 * @author zhaopeng
 * @date 2023/10/14 3:40
 **/
@SuppressWarnings("unused")
@Service
public record RedisService(RedisTemplate<String, Object> redisTemplate) {

    /**
     * 返回 Redis 的连接工厂
     *
     * @return RedisConnectionFactory 实例
     */
    public RedisConnectionFactory getRedisConnectionFactory() {
        return redisTemplate.getConnectionFactory();
    }

    /**
     * 返回 RedisTemplate 实例
     *
     * @return RedisTemplate 实例
     */
    @Override
    public RedisTemplate<String, Object> redisTemplate() {
        return redisTemplate;
    }

    /**
     * 判断 Key 是否存在
     * @param key K
     * @return true - 存在
     */
    public Boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 查询适配指定模式的所有 Key
     * @param keyPattern Key 模式
     * @return 适配指定模式的所有 Key
     */
    public Set<String> keys(final String keyPattern) {
        return redisTemplate.keys(keyPattern + "*");
    }

    /**
     * 删除 Key
     * @param keys 要删除的 Key 数组
     * @return 成功删除的 key 数目
     */
    public Long delete(final String... keys) {
        ArrayList<String> keyList = new ArrayList<>(keys.length);
        Collections.addAll(keyList, keys);
        return redisTemplate.delete(keyList);
    }

    /**
     * 设置 Key 过期时间
     * @param key K - 要求非空
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     */
    public void expire(final String key, final long time, final TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 设置 Key 过期时间
     * @param key K - 要求非空
     * @param time 过期时间，单位：秒
     */
    public void expire(final String key, final long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 清空 Redis 集群中指定的节点
     *
     * @param node Redis 集群节点
     */
    public void flush(RedisClusterNode node) {
        redisTemplate.opsForCluster().flushDb(node);
    }

    /**
     * 设置 key 为保存字符串 value 并设置 key 为在给定秒数后超时
     *
     * @param key   K - 要求非空
     * @param value V - 要求非空
     * @param time  过期时间，单位：秒
     */
    public void setExpire(final byte[] key, final byte[] value, final long time) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.stringCommands().setEx(key, time, value);
            return 1L;
        });
    }

    /**
     * 设置 key 的值和过期超时
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     */
    public void setExpire(final String key, final Object value, final long time, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 设置 key 的值和过期超时
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间，单位：秒
     */
    public void setExpire(final String key, final Object value, final long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置 key 的值和过期超时
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间
     * @param timeUnit 时间单位
     * @param valueSerializer 值序列化器
     */
    public void setExpire(final String key, final Object value, final long time, final TimeUnit timeUnit, RedisSerializer<Object> valueSerializer) {
        byte[] serializedKey = serializeKey(key);
        byte[] serializedValue = serializeValue(value, valueSerializer);

        redisTemplate.execute(connection -> new RedisCallback<>() {
            @Override
            public Object doInRedis(@NonNull RedisConnection connection) throws DataAccessException {
                execSetEx(connection);
                return null;
            }

            private void execSetEx(RedisConnection connection) {
                if (!TimeUnit.MILLISECONDS.equals(timeUnit) || !execPsetEx(connection)) {
                    connection.stringCommands().setEx(serializedKey, TimeoutUtils.toSeconds(time, timeUnit), serializedValue);
                }
            }

            /**
             * 执行 Pset 指令设置 KV 和过期时间，
             * 注意 Pset 指令只支持毫秒单位
             *
             * @param connection 连接
             * @return 是否执行成功
             */
            private boolean execPsetEx(RedisConnection connection) {
                boolean success = true;
                try {
                    connection.stringCommands().pSetEx(serializedKey, time, serializedValue);
                } catch (UnsupportedOperationException e) {
                    success = false;
                }
                return success;
            }
        }, true);
    }

    /**
     * 设置一组 key 的值和过期超时
     * @param keys Key 数组
     * @param values Value 数组
     * @param time 过期时间，单位：秒
     */
    public void setExpire(final String[] keys, final Object[] values, final long time) {
        for (int i = 0; i < keys.length; i++) {
            redisTemplate.opsForValue().set(keys[i], values[i], time, TimeUnit.SECONDS);
        }
    }

    /**
     * 设置 key 的值和过期超时
     * @param key K - 要求非空
     * @param value V - 要求非空
     */
    public void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置一组 key 的值和过期超时
     * @param keys Key 数组
     * @param values Value 数组
     */
    public void set(final String[] keys, final Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            redisTemplate.opsForValue().set(keys[i], values[i]);
        }
    }

    /**
     * 设置 Key 以在键不存在时保存 Value 值
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @return true - 设置成功
     */
    public Boolean setIfAbsent(final String key, final Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 设置 Key 以在键不存在时保存 Value 值，并设置过期时间
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     * @return true - 设置成功
     */
    public Boolean setIfAbsent(final String key, final Object value, final long time, final TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
    }

    /**
     * 设置 Key 以在键不存在时保存 Value 值，并设置过期时间
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间，单位：秒
     * @return true - 设置成功
     */
    public Boolean setIfAbsent(final String key, final Object value, final long time) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置 Key 以在键存在时保存 Value 值
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @return true - 设置成功
     */
    public Boolean setIfPresent(final String key, final Object value) {
        return redisTemplate.opsForValue().setIfPresent(key, value);
    }

    /**
     * 设置 Key 以在键存在时保存 Value 值，并设置过期时间
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     * @return true - 设置成功
     */
    public Boolean setIfPresent(final String key, final Object value, final long time, final TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfPresent(key, value, time, timeUnit);
    }

    /**
     * 设置 Key 以在键存在时保存 Value 值，并设置过期时间
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间，单位：秒
     * @return true - 设置成功
     */
    public Boolean setIfPresent(final String key, final Object value, final long time) {
        return redisTemplate.opsForValue().setIfPresent(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 查询 Key 对应的 Value
     * @param key K - 要求非空
     * @return Key 对应的 Value
     */
    public Object get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 查询 Key 对应的 Value
     * @param key K - 要求非空
     * @return Key 对应的 Value
     */
    public byte[] get(final byte[] key) {
        return redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.stringCommands().get(key));
    }

    /**
     * 设置 Key 对应的 Value 并返回 Key 对应的旧值
     * @param key K - 要求非空
     * @param value Value
     * @return Key 对应的旧值
     */
    public String getAndSet(final String key, String value) {
        Assert.hasText(key, "Key requirements are not empty");
        Object oldValue = redisTemplate.opsForValue().getAndSet(key, value);
        if (oldValue != null) {
            return oldValue.toString();
        }
        return null;
    }

    /**
     * 查询 Key 对应的 Value
     * @param key K - 要求非空
     * @param valueSerializer 值序列化器
     * @return Key 对应的值序列化器序列化后的 Value
     */
    public Object get(final String key, RedisSerializer<Object> valueSerializer) {
        byte[] serializedKey = serializeKey(key);
        return redisTemplate.execute(connection -> deserializeValue(connection.stringCommands().get(serializedKey), valueSerializer), true);
    }

    /**
     * 返回 HashOperations 实例
     * @return HashOperations 实例 - Redis 映射处理哈希的特定操作实例
     */
    public HashOperations<String, String, Object> opsForHash() {
        return redisTemplate.opsForHash();
    }

    /**
     * 设置哈希中哈希 Key 的 value
     * @param key Key - 要求非空
     * @param hashKey HashKey - 要求非空
     * @param value 哈希 Key 的 value
     */
    public void hSet(final String key, final String hashKey, final Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 批量设置哈希中哈希 Key 的 value
     * @param key Key - 要求非空
     * @param data Hash 数据 - 要求非空
     */
    public void hSet(final String key, final Map<String, Object> data) {
        redisTemplate.opsForHash().putAll(key, data);
    }

    /**
     * 当哈希中哈希 Key 不存在时才设置哈希 Key 的 Value
     * @param key Key - 要求非空
     * @param hashKey HashKey - 要求非空
     * @param value 哈希 Key 的 Value
     * @return true - 设置成功
     */
    public Boolean hSetIfAbsent(final String key, final String hashKey, final Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    /**
     * 删除哈希中指定的 HashKeys 对应的数据
     * @param key K - 要求非空
     * @param hashKeys HK - 要求非空
     */
    public void hDel(final String key, final Object... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 查询哈希中哈希 Key 的 value
     * @param key Key - 要求非空
     * @param hashKey HashKey - 要求非空
     * @return 哈希中哈希 Key 的 value
     */
    public Object hGet(final String key, final String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 返回指定哈希中的所有 HashKey
     * @param key Key - 要求非空
     * @return 指定哈希中的所有 HashKey
     */
    public Set<String> hKeys(final String key) {
        return redisTemplate.<String, Object>opsForHash().keys(key);
    }

    /**
     * 返回指定哈希中的所有数据
     * @param key Key - 要求非空
     * @return 指定哈希中的所有数据
     */
    public Map<String, Object> hGetAll(final String key) {
        return redisTemplate.<String, Object>opsForHash().entries(key);
    }

    /**
     * 返回指定哈希中的所有数据
     * @param key Key - 要求非空
     * @param redisSerializer 反序列化器
     * @return 指定哈希中的所有数据
     */
    public Map<String, Object> hGetAll(final String key, final RedisSerializer<Object> redisSerializer) {
        Assert.notNull(key, "Key requirements are not null");
        return redisTemplate.execute((RedisCallback<Map<String, Object>>) connection -> {
            Map<byte[], byte[]> serializedData = connection.hashCommands().hGetAll(key.getBytes());
            if (CollectionUtils.isEmpty(serializedData)) {
                return Collections.emptyMap();
            }
            Map<String, Object> data = new HashMap<>(serializedData.size());
            serializedData.forEach((k, v) -> data.put(deserializeValue(k, RedisSerializer.string()), deserializeValue(v, redisSerializer)));
            return data;
        });
    }

    /**
     * 返回哈希中指定 Hash Key 集合对应的所有 Hash Value 集合
     * @param key Key - 要求非空
     * @param hashKeys HK 集合 - 要求非空
     * @return 哈希中指定 Hash Key 集合对应的所有 Hash Value 集合
     */
    public List<Object> hGetAll(final String key, final Set<String> hashKeys) {
        return redisTemplate.<String, Object>opsForHash().multiGet(key, hashKeys);
    }

    /**
     * Hash Value 自增
     * @param key K - 要求非空
     * @param hashKey HK - 要求非空
     * @param value 增量
     * @return 自增结果
     */
    public long hIncr(final String key, final String hashKey, long value) {
        return redisTemplate.opsForHash().increment(key, hashKey, value);
    }

    /**
     * 序列化 Key
     * @param key K
     * @return 序列化结果
     * @param <T> K 类型
     */
    @SuppressWarnings("unchecked")
    private <T> byte[] serializeKey(T key) {
        Assert.notNull(key, "Key requirements are not null");
        if (key instanceof byte[]) {
            return (byte[]) key;
        }
        RedisSerializer<T> keySerializer = (RedisSerializer<T>) redisTemplate.getKeySerializer();
        return keySerializer.serialize(key);
    }

    /**
     * 序列化 Value
     * @param value 值
     * @param valueSerializer 序列化器
     * @return 序列化结果
     * @param <T> 值类型
     */
    private <T> byte[] serializeValue(T value, RedisSerializer<T> valueSerializer) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        return valueSerializer.serialize(value);
    }

    /**
     * 反序列化
     * @param serializedData 序列化数据
     * @param serializer 序列化器
     * @return 反序列化结果
     * @param <T> 结果类型
     */
    @SuppressWarnings("unchecked")
    private <T> List<T> deserializeValues(List<byte[]> serializedData, RedisSerializer<T> serializer) {
        if (serializer == null) {
            return (List<T>) serializedData;
        }
        return SerializationUtils.deserialize(serializedData, serializer);
    }

    /**
     * 反序列化
     * @param data 序列化数据
     * @param serializer 序列化器
     * @return 反序列化结果
     * @param <T> 结果类型
     */
    private <T> T deserializeValue(byte[] data, RedisSerializer<T> serializer) {
        if (serializer == null) {
            return (T) data;
        }
        return serializer.deserialize(data);
    }

}
