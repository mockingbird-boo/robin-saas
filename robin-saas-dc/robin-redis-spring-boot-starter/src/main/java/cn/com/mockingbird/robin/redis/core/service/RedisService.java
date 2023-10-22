package cn.com.mockingbird.robin.redis.core.service;

import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 服务工具
 * <p>
 * 底层主要用到了 lettuce 组件
 *
 * @author zhaopeng
 * @date 2023/10/14 3:40
 **/
@SuppressWarnings("unused")
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 删除数据
     * @param keys 要删除的键数组 - 要求非空
     * @return 被删除的键数量
     */
    public Long delete(final String... keys) {
        ArrayList<String> keyList = new ArrayList<>(keys.length);
        Collections.addAll(keyList, keys);
        return redisTemplate.delete(keyList);
    }

    /**
     * 清空 Redis 集群中指定的节点
     * @param node Redis 集群节点
     */
    public void flush(RedisClusterNode node) {
        redisTemplate.opsForCluster().flushDb(node);
    }

    /**
     * 设置数据过期时间
     * @param key 键 - 要求非空
     * @param time 过期时间
     * @param timeUnit 过期时间单位
     */
    public void expire(final String key, final long time, final TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    /**
     * 设置数据过期时间
     * @param key 键 - 要求非空
     * @param time 过期时间，单位：秒
     */
    public void expire(final String key, final long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 返回 Redis 的连接工厂
     * @return RedisConnectionFactory 实例
     */
    public RedisConnectionFactory getRedisConnectionFactory() {
        return redisTemplate.getConnectionFactory();
    }

    /**
     * 返回 RedisTemplate 实例
     * @return RedisTemplate 实例
     */
    public RedisTemplate<String, Object> redisTemplate() {
        return redisTemplate;
    }

    /**
     * 判断数据是否存在
     * @param key 键 - 要求非空
     * @return true - 存在
     */
    public Boolean hasKey(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 查询适配指定模式的键集合
     * @param keyPattern 模式
     * @return 键集合
     */
    public Set<String> keys(final String keyPattern) {
        return redisTemplate.keys(keyPattern + "*");
    }

}
