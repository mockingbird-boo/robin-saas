package cn.com.mockingbird.robin.redis.core.service;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis 服务工具
 *
 * @author zhaopeng
 * @date 2023/10/14 3:40
 **/
public record RedisService(RedisTemplate<String, Object> redisTemplate) {

    /**
     * 获取 Redis 的连接工厂
     *
     * @return RedisConnectionFactory 实例
     */
    public RedisConnectionFactory getRedisConnectionFactory() {
        return redisTemplate.getConnectionFactory();
    }

    /**
     * 获取 RedisTemplate 实例
     *
     * @return RedisTemplate 实例
     */
    @Override
    public RedisTemplate<String, Object> redisTemplate() {
        return redisTemplate;
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
     * @param time  超时时间，单位：秒
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
     * @param time 超时时间
     * @param timeUnit 超时时间单位
     */
    public void setExpire(final String key, final Object value, final long time, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 设置 key 的值和过期超时
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 超时时间，单位：秒
     */
    public void setExpire(final String key, final Object value, final long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置 key 的值和过期超时
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 超时时间
     * @param timeUnit 时间单位
     * @param valueSerializer 值序列化器
     */
    public void setExpire(final String key, final Object value, final long time, final TimeUnit timeUnit, RedisSerializer<Object> valueSerializer) {
        byte[] serializedKey = serializeKey(key);
        byte[] serializedValue = serializeValue(value, valueSerializer);

        redisTemplate.execute(new RedisCallback<>() {
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
             * 执行 Pset 指令设置 KV 和超时时间，
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
     * @param time 超时时间，单位：秒
     */
    public void setExpire(final String[] keys, final Object[] values, final long time) {
        for (int i = 0; i < keys.length; i++) {
            redisTemplate.opsForValue().set(keys[i], values[i], time, TimeUnit.SECONDS);
        }
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
