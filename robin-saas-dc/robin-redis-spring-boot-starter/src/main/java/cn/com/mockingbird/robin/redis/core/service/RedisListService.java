package cn.com.mockingbird.robin.redis.core.service;

import cn.com.mockingbird.robin.redis.core.util.SerialUtils;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Collection;
import java.util.List;

/**
 * List（列表）数据类型服务工具
 * <p>
 * 适用于常用的数据结构（比如：栈 FILO = LPUSH + LPOP、队列 FIFO = LPUSH + RPOP、
 * 阻塞队列 = LPUSH + BRPOP 等）。
 * <p>
 * 还可以适用于消息流（LPUSH + LRANGE 百万千万级数据量不合适）等场景。
 *
 * @author zhaopeng
 * @date 2023/10/22 15:38
 **/
@SuppressWarnings("unused")
public class RedisListService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisListService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 返回 ListOperations 实例
     * @return ListOperations 实例
     */
    public ListOperations<String, Object> opsForList() {
        return redisTemplate.opsForList();
    }

    /**
     * 从左边增加元素
     * @param key 键 - 要求非空
     * @param value 元素
     * @return 增加元素后列表的长度
     */
    public Long leftPush(final String key, final Object value) {
        return this.opsForList().leftPush(key, value);
    }

    /**
     * 从左边批量增加元素
     * @param key 键 - 要求非空
     * @param values 元素数组
     * @return 增加元素后列表的长度
     */
    public Long leftPushAll(final String key, final Object... values) {
        return this.opsForList().leftPushAll(key, values);
    }

    /**
     * 从左边批量增加元素
     *
     * @param key 键 - 要求非空
     * @param values 元素集合 - 要求非空
     * @return 增加元素后列表的长度
     */
    public Long leftPushAll(final String key, final Collection<Object> values) {
        return this.opsForList().leftPushAll(key, values);
    }

    /**
     * 从右边增加元素
     * @param key 键 - 要求非空
     * @param value 元素
     * @return 增加元素后列表的长度
     */
    public Long rightPush(final String key, final Object value) {
        return this.opsForList().rightPush(key, value);
    }

    /**
     * 从右边批量增加元素
     * @param key 键 - 要求非空
     * @param values 元素数组
     * @return 增加元素后列表的长度
     */
    public Long rightPushAll(final String key, final Object... values) {
        return this.opsForList().rightPushAll(key, values);
    }

    /**
     * 从右边批量增加元素
     *
     * @param key 键 - 要求非空
     * @param values 元素集合 - 要求非空
     * @return 增加元素后列表的长度
     */
    public Long rightPushAll(final String key, final Collection<Object> values) {
        return this.opsForList().rightPushAll(key, values);
    }

    /**
     * 删除并返回存储在列表中的第一个元素
     * @param key 键 - 要求非空
     * @return 原本存储在列表中的第一个元素
     */
    public Object leftPop(final String key) {
        return this.opsForList().leftPop(key);
    }

    /**
     * 删除并返回存储在指定 Key 的列表中的末尾元素
     * @param key 键 - 要求非空
     * @return 原本存储在列表中的末尾元素
     */
    public Object rightPop(final String key) {
        return this.opsForList().rightPop(key);
    }

    /**
     * 删除存储在列表中的元素
     *
     * @param key 键 - 要求非空
     * @param count 数量，大于0 - 从左到右删除 count 个 value 元素；
     *                   小于0 - 从右到左删除 count 个 value 元素；
     *                   等于0 - 删除所有 value 元素
     * @param value 元素
     * @return 删除元素的数量
     */
    public Long remove(final String key, final long count, final Object value) {
        return this.opsForList().remove(key, count, value);
    }

    /**
     * 删除列表
     * @param key 键 - 要求非空
     * @return true - 删除成功
     */
    public Boolean delete(final String key) {
        if (redisTemplate.type(key) == DataType.LIST) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    /**
     * 设置列表索引处的元素
     *
     * @param key 键 - 要求非空
     * @param index 索引
     * @param value 元素
     */
    public void set(final String key, final long index, final Object value) {
        this.opsForList().set(key, index, value);
    }

    /**
     * 返回列表的大小
     * @param key 键 - 要求非空
     * @return 列表的大小
     */
    public Long size(final String key) {
        return this.opsForList().size(key);
    }

    /**
     * 返回列表索引处的元素
     * @param key 键 - 要求非空
     * @param index 索引
     * @return 列表索引处的元素
     */
    public Object get(final String key, final int index) {
        return this.opsForList().index(key, index);
    }

    /**
     * 返回指定范围内的元素列表
     * <p>
     * 列表的初始索引为 0
     * @param key 键 - 要求非空
     * @param start 开始索引，如果大于列表末尾，则返回空列表
     * @param end 结束索引（包含）
     * @return 指定范围内的元素列表
     */
    public List<Object> range(final String key, final int start, final int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 返回指定范围内的元素列表
     * @param key K - 要求非空
     * @param start 开始索引，如果大于列表末尾，则返回空列表
     * @param end 结束索引（包含）
     * @param valueSerializer 值序列化器
     * @return 指定范围内的元素列表
     */
    public List<Object> range(final String key, final int start, final int end, RedisSerializer<Object> valueSerializer) {
        byte[] rawKey = SerialUtils.serializeKey(key);
        return redisTemplate.execute(connection ->
                SerialUtils.deserializeValues(connection.listCommands().lRange(rawKey, start, end), valueSerializer),true);
    }
}
