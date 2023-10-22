package cn.com.mockingbird.robin.redis.core.service;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.Set;

/**
 * ZSet （权重集合，也叫排序集合）数据类型服务工具
 * <p>
 * 适用于热搜版或者排行榜的类似场景（搜索/浏览一次，权重+1，也支持范围查找，同时和 Set 一样支持集合运算）。
 *
 * @author zhaopeng
 * @date 2023/10/22 15:25
 **/
@SuppressWarnings("unused")
public class RedisZSetService {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisZSetService() {

    }

    public RedisZSetService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 返回 ZSetOperations 实例
     * @return ZSetOperations 实例
     */
    @SuppressWarnings("all")
    public ZSetOperations<String, Object> opsForZSet() {
        return redisTemplate.opsForZSet();
    }

    /**
     * 增加带权重的元素，当元素存在则更新该元素权重
     * @param key 键 - 不能为空
     * @param value 元素
     * @param score 权重
     * @return true - 增加元素或更新权重成功
     */
    public Boolean add(final String key, final Object value, final double score) {
        return this.opsForZSet().add(key, value, score);
    }

    /**
     * 批量增加元素
     * @param key 键 - 不能为空
     * @param tuples 元组
     * @return 变化的元素数量（add or update）
     */
    public Long add(final String key, final Set<ZSetOperations.TypedTuple<Object>> tuples) {
        return this.opsForZSet().add(key, tuples);
    }

    /**
     * 删除元素
     * @param key 键 - 要求非空
     * @param value 元素 - 要求非空
     * @return 被删除的元素数量
     */
    public Long remove(final String key, final Object value) {
        return this.opsForZSet().remove(key, value);
    }

    /**
     * 批量删除元素
     * @param key 键 - 要求非空
     * @param values 元素数组 - 要求非空
     * @return 被删除的元素数量
     */
    public Long remove(final String key, final Object... values) {
        return this.opsForZSet().remove(key, values);
    }

    /**
     * 删除集合
     * @param key 键 - 要求非空
     * @return true - 删除成功
     */
    public Boolean delete(final String key) {
        if (redisTemplate.type(key) == DataType.ZSET) {
            return redisTemplate.delete(key);
        }
        return false;
    }

    /**
     * 递增元素的权重，其值按增量排序设置，当元素不存在时，会新增一个元素
     * @param key 键 - 要求非空
     * @param value 元素
     * @param delta 增量
     * @return 递增之后元素的权重
     */
    public Double incrementScore(final String key, final Object value, double delta) {
        return this.opsForZSet().incrementScore(key, value, delta);
    }

    /**
     * 返回两个集合的并集
     * @param key 键 - 要求非空
     * @param otherKey 另一个键 - 要求非空
     * @return 两个集合的并集
     */
    public Set<Object> union(String key, String otherKey) {
        return this.opsForZSet().union(key, otherKey);
    }

    /**
     * 返回多个集合的并集
     * @param key 键 - 要求非空
     * @param otherKeys 另外多个集合的键集合
     * @return 多个集合的并集
     */
    public Set<Object> union(final String key, Collection<String> otherKeys) {
        return this.opsForZSet().union(key, otherKeys);
    }

    /**
     * 返回两个集合的交集
     * @param key 键 - 要求非空
     * @param otherKey 另一个键 - 要求非空
     * @return 两个集合的交集
     */
    public Set<Object> intersect(String key, String otherKey) {
        return this.opsForZSet().intersect(key, otherKey);
    }

    /**
     * 返回多个集合的交集
     * @param key 键 - 要求非空
     * @param otherKeys 另外多个集合的键集合
     * @return 多个集合的交集
     */
    public Set<Object> intersect(final String key, Collection<String> otherKeys) {
        return this.opsForZSet().intersect(key, otherKeys);
    }

    /**
     * 返回两个集合的差集
     * @param key 键 - 要求非空
     * @param otherKey 另一个键 - 要求非空
     * @return 两个集合的差集
     */
    public Set<Object> difference(String key, String otherKey) {
        return this.opsForZSet().difference(key, otherKey);
    }

    /**
     * 返回多个集合的差集
     * @param key 键 - 要求非空
     * @param otherKeys 另外多个集合的键集合
     * @return 多个集合的差集
     */
    public Set<Object> difference(final String key, Collection<String> otherKeys) {
        return this.opsForZSet().difference(key, otherKeys);
    }

    /**
     * 返回元素的权重
     * @param key 键 - 要求非空
     * @param value 元素
     * @return 元素的权重
     */
    public Double score(final String key, final Object value) {
        return this.opsForZSet().score(key, value);
    }

    /**
     * 返回元素在集合中的排名
     * @param key 键 - 要求非空
     * @param value 元素
     * @return 元素在集合中的排名
     */
    public Long rank(final String key, final Object value) {
        return this.opsForZSet().rank(key, value);
    }

    /**
     * 根据索引范围返回元素集合，权重小的排在前面
     * <p>
     * 例如，range(key, 0, 1) 将返回排序集的第一个和第二个元素
     *
     * @param key 键 - 要求非空
     * @param start 开始索引，如果 start 大于排序集的结束索引或 stop，则返回空列表
     * @param end 结束索引，如果 stop 大于排序集的结束索引，将使用排序集的最后一个元素
     * @return 指定范围的元素集合
     */
    public Set<Object> range(final String key, final long start, final long end) {
        return this.opsForZSet().range(key, start, end);
    }

    /**
     * 根据索引范围返回元组（元素 + 权重）集合，权重小的排在前面
     * @param key key 键 - 要求非空
     * @param start 开始索引，如果 start 大于排序集的结束索引或 stop，则返回空列表
     * @param end 结束索引，如果 stop 大于排序集的结束索引，将使用排序集的最后一个元素
     * @return 指定范围的元组集合
     */
    public Set<ZSetOperations.TypedTuple<Object>> rangeWithScore(final String key, final long start, final long end) {
        return this.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * 根据索引范围返回元素集合，权重大的排在前面
     * <p>
     * 例如，range(key, 0, 1) 将返回排序集的第一个和第二个元素
     *
     * @param key 键 - 要求非空
     * @param start 开始索引，如果 start 大于排序集的结束索引或 stop，则返回空列表
     * @param end 结束索引，如果 stop 大于排序集的结束索引，将使用排序集的最后一个元素
     * @return 指定范围的元素集合
     */
    public Set<Object> reverseRange(final String key, final long start, final long end) {
        return this.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 根据权重范围返回元素集合，权重小的排在前面
     * @param key 键 - 要求非空
     * @param min 权重下限（包含）
     * @param max 权重上限（包含）
     * @return 指定权重范围返回元素集合
     */
    public Set<Object> rangeByScore(final String key, final double min, final double max) {
        return this.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 根据权重范围返回元素集合，权重小的排在前面
     * @param key 键 - 要求非空
     * @param min 权重下限（包含）
     * @param max 权重上限（包含）
     * @return 指定权重范围返回元素集合
     */
    public Set<Object> reverseRangeByScore(final String key, final double min, final double max) {
        return this.opsForZSet().reverseRangeByScore(key, min, max);
    }


    /**
     * 返回集合的大小
     * @param key 键 - 要求非空
     * @return 集合的大小
     */
    public Long size(final String key) {
        return this.opsForZSet().size(key);
    }

}
