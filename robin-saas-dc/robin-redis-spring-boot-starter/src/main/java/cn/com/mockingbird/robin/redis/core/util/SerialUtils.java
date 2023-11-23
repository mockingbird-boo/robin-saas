package cn.com.mockingbird.robin.redis.core.util;

import cn.com.mockingbird.robin.common.constant.Standard;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    /**
     * 返回 GenericJackson2JsonRedisSerializer
     * @return GenericJackson2JsonRedisSerializer 实例
     */
    public static GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        // 允许更改底层 VisibilityCheckers 的配置，所有成员字段无需进一步注释即可序列化，而不仅仅是公共字段（默认设置）
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化类必须是非 final 修饰的类
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        // LocalDatetime 类型处理
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Standard.DateTimePattern.MS_DATETIME)));
        timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(Standard.DateTimePattern.DATE)));
        timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Standard.DateTimePattern.MS_DATETIME)));
        timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(Standard.DateTimePattern.DATE)));
        objectMapper.registerModule(timeModule);

        // 禁用 DATE_AS_TIMESTAMPS
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        /*
         * 采用 GenericJackson2JsonRedisSerializer 序列化方式对于 String、对象、对象数组、JSONObject、JSONArray 的序列化反序列化操作一般都是正常，支持强转，
         * 而采用 Jackson2JsonRedisSerializer 序列化方式在没有 ObjectMapper 配置时进行对象强转容易发生报错
         */
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

}
