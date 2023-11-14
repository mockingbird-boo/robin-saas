package cn.com.mockingbird.robin.redis.core.service;

import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;

import java.util.Arrays;
import java.util.Map;

/**
 * Redis Stream Service 工具
 *
 * @author zhaopeng
 * @date 2023/11/14 20:32
 **/
@SuppressWarnings("unused")
public class RedisStreamService {

    private RedisTemplate<String, Object> redisTemplate;

    private static final String SUCCESS = "OK";

    public RedisStreamService() {

    }

    public RedisStreamService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 返回 StreamOperations 实例
     * @return StreamOperations 实例
     */
    public StreamOperations<String, String, Object> opsForStream() {
        return redisTemplate.opsForStream();
    }

    /**
     * 发布内容
     * @param key 流数据的 Key
     * @param content 内容
     * @return 记录 ID
     */
    public RecordId add(String key, Map<String, Object> content) {
        return this.opsForStream().add(key, content);
    }

    /**
     * 发布记录。该值映射为哈希并序列化。
     * @param record 记录 - 要求非空
     * @return 记录 ID
     */
    public RecordId add(Record<String, Object> record) {
        return this.opsForStream().add(record);
    }

    /**
     * 从流中删除指定的记录
     * @param key 流数据的 Key
     * @param recordIds 要删除的记录的 ID 数组
     * @return 已删除的记录数，如果某些 ID 不存在，则该数可能与传递的 ID 数不同
     */
    public Long delete(String key, String... recordIds) {
        return this.opsForStream().delete(key, recordIds);
    }

    /**
     * 从流中删除指定的记录
     * @param key 流数据的 Key
     * @param recordIds 要删除的记录的 ID 数组
     * @return 已删除的记录数，如果某些 ID 不存在，则该数可能与传递的 ID 数不同
     */
    public Long delete(String key, RecordId... recordIds) {
        return delete(key, Arrays.stream(recordIds).map(RecordId::getValue).toArray(String[]::new));
    }

    /**
     * 从流中删除指定的记录
     * @param record 要删除的记录
     * @return 已删除的记录数
     */
    public Long delete(Record<String, Object> record) {
        return this.opsForStream().delete(record);
    }

    /**
     * 创建消费者组，如果流尚不存在，将创建流
     * @param key 流数据的 Key
     * @param group 消费者组
     * @return true - 创建成功
     */
    public Boolean createGroup(String key, String group) {
        return SUCCESS.equals(this.opsForStream().createGroup(key, group));
    }

    /**
     * 销毁消费者组
     * @param key 流数据的 Key
     * @param group 消费者组
     * @return true - 销毁成功
     */
    public Boolean destroyGroup(String key, String group) {
        return this.opsForStream().destroyGroup(key, group);
    }

    /**
     * 确认一条或多条记录已处理
     * @param key 流数据的 Key
     * @param group 消费者组
     * @param recordIds 要进行确认的记录 id 数组
     * @return 确认过的记录数
     */
    public Long ack(String key, String group, String... recordIds) {
        return this.opsForStream().acknowledge(key, group, recordIds);
    }

    /**
     * 确认一条或多条记录已处理
     * @param key 流数据的 Key
     * @param group 消费者组
     * @param recordIds 要进行确认的记录 id 数组
     * @return 确认过的记录数
     */
    public Long ack(String key, String group, RecordId... recordIds) {
        return this.opsForStream().acknowledge(key, group, recordIds);
    }

}
