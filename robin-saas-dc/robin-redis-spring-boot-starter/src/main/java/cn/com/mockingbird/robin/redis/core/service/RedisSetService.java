package cn.com.mockingbird.robin.redis.core.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Set（集合）数据类型服务工具
 * <p>
 * 适用于抽奖程序（SADD - 参与抽奖、SMEMBERS - 查看所有抽奖用户、
 * SRANDMEMBER key [count] / SPOP key [count] - 随机抽取 count 名中奖者）等场景。
 * <p>
 * 也适用于文章点赞、喜欢、收藏（文章ID -> 用户IDs）这种场景。
 * <p>
 * 也适用于关注模型类似的场景，比如“我关注的人”，“她关注的人”，“我和她共同关注的人”，“我关注的人也关注她”，“可能认识”等等。
 * <p>
 * 还适用于电商商品筛选的场景：
 * SADD  brand:huawei  P30   // P30放到华为品牌筛选条件下
 * SADD  brand:xiaomi  mi-6X  // mi-6x放到小米品牌筛选条件下
 * SADD  brand:iPhone iphone8  // iphone8 放到iphone品牌筛选条件下
 * SADD  os:android  P30  mi-6X  // P30，mi-6X 放到安卓操作系统筛选条件下
 * SADD  cpu:brand:intel  P30  mi-6X  // P30，mi-6X 放到CPU使用intel筛选条件下
 * SADD  ram:8G  P30  mi-6X  iphone8  // P30，mi-6X iphone8都是8G手机，就放到8G这个筛选条件下
 * 当要找安卓系统，CPU使用intel然后内存8G的手机，只要执行下面的命令：
 * SINTER  os:android  cpu:brand:intel  ram:8G   // 最终找到这两个手机 {P30，mi-6X}
 *
 * @author zhaopeng
 * @date 2023/10/22 15:29
 **/
@SuppressWarnings("unused")
public class RedisSetService {

    private RedisTemplate<String, Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisSetService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisSetService() {
    }

    /**
     * 返回 SetOperations 实例
     * @return SetOperations 实例
     */
    public SetOperations<String, Object> opsForSet() {
        return redisTemplate.opsForSet();
    }

    /**
     * 增加元素
     * @param key 键 - 要求非空
     * @param value 元素
     * @return 增加到集合中的元素数，不包括集合中已存在的所有元素
     */
    public Long add(final String key, final Object value) {
        return this.opsForSet().add(key, value);
    }

    /**
     * 批量增加元素
     * @param key 键 - 要求非空
     * @param values 元素数组
     * @return 添加到集合中的元素数，不包括集合中已存在的所有元素
     */
    public Long add(final String key, final Object... values) {
        return this.opsForSet().add(key, values);
    }

    /**
     * 删除元素
     * @param key 键 - 要求非空
     * @param value 元素 - 要求非空
     * @return 被删除元素的数量
     */
    public Long remove(final String key, final Object value) {
        return this.opsForSet().remove(key, value);
    }

    /**
     * 批量删除元素
     * @param key 键 - 要求非空
     * @param values 元素数组
     * @return 被删除元素的数量
     */
    public Long remove(final String key, final Object... values) {
        return this.opsForSet().remove(key, values);
    }

    /**
     * 从集合中随机删除指定数量的元素，并会将删除的元素返回
     * @param key 键 - 要求非空
     * @param count 数量
     * @return 被删除的元素集合
     */
    public List<Object> pop(final String key, final long count) {
        return this.opsForSet().pop(key, count);
    }

    /**
     * 从集合中随机删除一个倒霉元素，并会将该元素返回
     * @param key 键 - 要求非空
     * @return 被删除的元素
     */
    public Object pop(final String key) {
        return this.opsForSet().pop(key);
    }

    /**
     * 返回两个集合的并集
     * @param key 键 - 要求非空
     * @param otherKey 另一个键 - 要求非空
     * @return 两个集合的并集
     */
    public Set<Object> union(String key, String otherKey) {
        return this.opsForSet().union(key, otherKey);
    }

    /**
     * 返回多个集合的并集
     * @param keys 多个集合的键集合
     * @return 多个集合的并集
     */
    public Set<Object> union(Collection<String> keys) {
        return this.opsForSet().union(keys);
    }

    /**
     * 返回两个集合的交集
     * @param key 键 - 要求非空
     * @param otherKey 另一个键 - 要求非空
     * @return 两个集合的交集
     */
    public Set<Object> intersect(String key, String otherKey) {
        return this.opsForSet().intersect(key, otherKey);
    }

    /**
     * 返回多个集合的交集
     * @param keys 多个集合的键集合
     * @return 多个集合的交集
     */
    public Set<Object> intersect(Collection<String> keys) {
        return this.opsForSet().intersect(keys);
    }

    /**
     * 返回两个集合的差集
     * @param key 键 - 要求非空
     * @param otherKey 另一个键 - 要求非空
     * @return 两个集合的差集
     */
    public Set<Object> difference(String key, String otherKey) {
        return this.opsForSet().difference(key, otherKey);
    }

    /**
     * 返回多个集合的差集
     * @param keys 多个集合的键集合
     * @return 多个集合的差集
     */
    public Set<Object> difference(Collection<String> keys) {
        return this.opsForSet().difference(keys);
    }

    /**
     * 判断元素是否存在
     * @param key 键 - 要求非空
     * @param value 元素
     * @return true - 存在
     */
    public Boolean isMember(final String key, final Object value) {
        return this.opsForSet().isMember(key, value);
    }

    /**
     * 获取集合中的所有元素
     * @param key 键 - 要求非空
     * @return 集合中所有元素
     */
    public Set<Object> members(final String key) {
        return this.opsForSet().members(key);
    }

    /**
     * 从集合中随机返回指定数量的元素
     * @param key 键
     * @param count 返回元素的数量
     * @return 指定数量的元素
     */
    public List<Object> randomMembers(final String key, final long count) {
        return this.opsForSet().randomMembers(key, count);
    }

    /**
     * 从集合中随机返回一个幸运元素
     * @param key 键
     * @return 指定数量的元素
     */
    public Object randomMember(final String key) {
        return this.opsForSet().randomMember(key);
    }

    /**
     * 返回集合的大小
     * @param key 键 - 要求非空
     * @return 集合的大小
     */
    public Long size(final String key) {
        return this.opsForSet().size(key);
    }

}
