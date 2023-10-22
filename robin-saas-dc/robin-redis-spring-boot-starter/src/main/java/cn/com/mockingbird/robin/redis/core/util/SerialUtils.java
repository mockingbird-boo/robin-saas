package cn.com.mockingbird.robin.redis.core.util;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 序列化工具类
 *
 * @author zhaopeng
 * @date 2023/10/22 15:42
 **/
public final class SerialUtils {

    /**
     * 序列化 Key
     * @param key K
     * @return 序列化数据
     */
    public static byte[] serializeKey(String key) {
        Assert.notNull(key, "Key requirements are not null");
        return RedisSerializer.string().serialize(key);
    }

    /**
     * 序列化 Value
     * @param value 值
     * @param valueSerializer 值序列化器
     * @return 序列化结果
     * @param <T> 值类型
     */
    public static <T> byte[] serializeValue(T value, RedisSerializer<T> valueSerializer) {
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
    public static <T> List<T> deserializeValues(List<byte[]> serializedData, RedisSerializer<T> serializer) {
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
    public static <T> T deserializeValue(byte[] data, RedisSerializer<T> serializer) {
        if (serializer == null) {
            return (T) data;
        }
        return serializer.deserialize(data);
    }

}
