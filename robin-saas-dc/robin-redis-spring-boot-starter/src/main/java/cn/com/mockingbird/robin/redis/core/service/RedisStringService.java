package cn.com.mockingbird.robin.redis.core.service;

import cn.com.mockingbird.robin.redis.core.util.SerialUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * String （值类型）数据类型服务工具 - 键值操作
 * <p>
 * 适用于单值缓存、对象缓存、计数器、全局序列号等场景，以及
 * 像 ${@link RedisStringService#setIfAbsent(String, Object, long, TimeUnit)} 这种方法
 * 也可用于简单场景的分布式锁。
 *
 * @author zhaopeng
 * @date 2023/10/22 16:04
 **/
@SuppressWarnings("unused")
public class RedisStringService {

    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 返回 ValueOperations 实例
     * @return ValueOperations 实例
     */
    public ValueOperations<String, Object> opsForValue() {
        return redisTemplate.opsForValue();
    }

    /**
     * 设置键值
     * @param key K - 要求非空
     * @param value V - 要求非空
     */
    public void set(final String key, final Object value) {
        this.opsForValue().set(key, value);
    }

    /**
     * 设置键值
     * @param key key 的序列化数据，要求非空
     * @param value value 的序列化数据，要求非空
     * @param time 过期时间，单位：秒
     */
    public void set(final byte[] key, final byte[] value, final long time) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            connection.stringCommands().setEx(key, time, value);
            return 1L;
        });
    }

    /**
     * 批量设置一组键值
     * @param keys Key 数组
     * @param values Value 数组
     */
    public void set(final String[] keys, final Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            this.opsForValue().set(keys[i], values[i]);
        }
    }

    /**
     * 批量设置一组键值
     * @param values 键值 Map，注意 Map 中的元素 Key 和 value 都要求非空
     */
    public void set(final Map<String, Object> values) {
        values.forEach((k, v) -> this.opsForValue().set(k, v));
    }

    /**
     * 设置键值以及数据过期超时
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     */
    public void set(final String key, final Object value, final long time, final TimeUnit timeUnit) {
        this.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 设置键值以及数据过期超时
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间，单位：秒
     */
    public void set(final String key, final Object value, final long time) {
        this.set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置键值以及数据过期超时
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间
     * @param timeUnit 时间单位
     * @param valueSerializer 值序列化器
     */
    public void set(final String key, final Object value, final long time, final TimeUnit timeUnit, RedisSerializer<Object> valueSerializer) {
        // 键值序列化
        byte[] serializedKey = SerialUtils.serializeKey(key);
        byte[] serializedValue = SerialUtils.serializeValue(value, valueSerializer);
        redisTemplate.execute(connection -> new RedisCallback<Object>() {
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
     * 批量设置一组键值及过期超时
     * @param keys Key 数组
     * @param values Value 数组
     * @param time 过期时间，单位：秒
     */
    public void set(final String[] keys, final Object[] values, final long time) {
        this.set(keys, values, time, TimeUnit.SECONDS);
    }

    /**
     * 批量设置一组键值及过期超时
     * @param keys Key 数组
     * @param values Value 数组
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     */
    public void set(final String[] keys, final Object[] values, final long time, TimeUnit timeUnit) {
        for (int i = 0; i < keys.length; i++) {
            this.set(keys[i], values[i], time, timeUnit);
        }
    }

    /**
     * 批量设置一组键值及过期超时
     * @param values 键值 Map，注意 Map 中的元素 Key 和 value 都要求非空
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     */
    public void set(final Map<String, Object> values, final long time, TimeUnit timeUnit) {
        values.forEach((k, v) -> this.set(k, v, time, timeUnit));
    }

    /**
     * 批量设置一组键值及过期超时
     * @param values 键值 Map，注意 Map 中的元素 Key 和 value 都要求非空
     * @param time 过期时间，单位：秒
     */
    public void set(final Map<String, Object> values, final long time) {
        this.set(values, time, TimeUnit.SECONDS);
    }

    /**
     * 设置键值，键不存在时才设置成功
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @return true - 设置成功
     */
    public Boolean setIfAbsent(final String key, final Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 设置键值及过期时间，键不存在时才设置成功
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
     * 设置键值及过期时间，键不存在时才设置成功
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间，单位：秒
     * @return true - 设置成功
     */
    public Boolean setIfAbsent(final String key, final Object value, final long time) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置键值，键存在时才设置成功
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @return true - 设置成功
     */
    public Boolean setIfPresent(final String key, final Object value) {
        return redisTemplate.opsForValue().setIfPresent(key, value);
    }

    /**
     * 设置键值及过期时间，键存在时才设置成功
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
     * 设置键值及过期时间，键存在时才设置成功
     * @param key K - 要求非空
     * @param value V - 要求非空
     * @param time 过期时间，单位：秒
     * @return true - 设置成功
     */
    public Boolean setIfPresent(final String key, final Object value, final long time) {
        return redisTemplate.opsForValue().setIfPresent(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 将指定键下存储为字符串的整数值递增 1
     * @param key K - 要求非空
     * @return increment 后的键值
     */
    public Long increment(final String key) {
        return this.opsForValue().increment(key);
    }

    /**
     * 按增量递增指定键下存储为字符串的整数值
     * @param key K - 要求非空
     * @param delta 增量
     * @return increment 后的键值
     */
    public Long increment(final String key, final long delta) {
        return this.opsForValue().increment(key, delta);
    }

    /**
     * 按增量递增指定键下存储为字符串的浮点数值
     * @param key K - 要求非空
     * @param delta 增量
     * @return increment 后的键值
     */
    public Double increment(final String key, final double delta) {
        return this.opsForValue().increment(key, delta);
    }

    /**
     * 删除键值
     * @param key K - 要求非空
     * @return true - 删除成功
     */
    public Boolean delete(final String key) {
        if (redisTemplate.type(key) == DataType.STRING) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    /**
     * 查询键值
     * @param key K - 要求非空
     * @return Key 对应的 Value
     */
    public Object get(final String key) {
        return this.opsForValue().get(key);
    }

    /**
     * 查询键值
     * @param key 键（序列化数据）
     * @return 值（序列化数据）
     */
    public byte[] get(final byte[] key) {
        return redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.stringCommands().get(key));
    }

    /**
     * 设置键值并返回键对应的旧值
     * @param key K - 要求非空
     * @param value Value
     * @return 旧值
     */
    public String getAndSet(final String key, String value) {
        Assert.hasText(key, "Key requirements are not empty");
        Object oldValue = this.opsForValue().getAndSet(key, value);
        if (oldValue != null) {
            return oldValue.toString();
        }
        return null;
    }

    /**
     * 查询键值
     * @param key K - 要求非空
     * @param valueSerializer 值序列化器
     * @return Key 对应的值序列化器序列化后的 Value
     */
    public Object get(final String key, RedisSerializer<Object> valueSerializer) {
        byte[] serializedKey = SerialUtils.serializeKey(key);
        return redisTemplate.execute(connection -> SerialUtils.deserializeValue(connection.stringCommands().get(serializedKey), valueSerializer), true);
    }

}
