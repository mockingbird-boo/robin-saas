package cn.com.mockingbird.robin.mybatis.base;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.Collection;
import java.util.List;

/**
 * mybatis-plus BaseMapper 的扩展接口
 * TODO 还可以增加分页查询功能的扩展
 *
 * @author zhaopeng
 * @date 2023/10/6 23:08
 **/
public interface BaseMapperX<T> extends BaseMapper<T> {

    default T selectOne(String field, Object value) {
        return selectOne(new QueryWrapper<T>().eq(field, value));
    }

    default T selectOne(SFunction<T, ?> field, Object value) {
        return selectOne(new LambdaQueryWrapper<T>().eq(field, value));
    }

    default T selectOne(String field1, Object value1, String field2, Object value2) {
        return selectOne(new QueryWrapper<T>().eq(field1, value1).eq(field2, value2));
    }

    default T selectOne(SFunction<T, ?> field1, Object value1, SFunction<T, ?> field2, Object value2) {
        return selectOne(new LambdaQueryWrapper<T>().eq(field1, value1).eq(field2, value2));
    }

    default Long selectCount() {
        return selectCount(new QueryWrapper<T>());
    }

    default Long selectCount(String field, Object value) {
        return selectCount(new QueryWrapper<T>().eq(field, value));
    }

    default Long selectCount(SFunction<T, ?> field, Object value) {
        return selectCount(new LambdaQueryWrapper<T>().eq(field, value));
    }

    /**
     * 查询全部数据
     * @return 全部数据的 list 集合
     */
    default List<T> selectAll() {
        return selectList(new QueryWrapper<>());
    }

    default List<T> selectList(String field, Object value) {
        return selectList(new QueryWrapper<T>().eq(field, value));
    }

    default List<T> selectList(SFunction<T, ?> field, Object value) {
        return selectList(new LambdaQueryWrapper<T>().eq(field, value));
    }

    default List<T> selectList(String field, Collection<?> values) {
        return selectList(new QueryWrapper<T>().in(field, values));
    }

    default List<T> selectList(SFunction<T, ?> field, Collection<?> values) {
        return selectList(new LambdaQueryWrapper<T>().in(field, values));
    }

    /**
     * 逐条批量新增
     * 适合少量数据插入，或者对性能要求不高的场景
     * 如果大量，可以使用：
     * {@link com.baomidou.mybatisplus.extension.service.impl.ServiceImpl#saveBatch(Collection)} 方法
     * 或者：{@link BaseMapperX#insertBatchSomeColumn(List)}
     * @param entities 数据集合
     */
    default void insertBatch(Collection<T> entities) {
        entities.forEach(this::insert);
    }

    /**
     * SQL 级批量新增
     * 注意如果发生报错，可以阅读源码 {@link InsertBatchSomeColumn}
     * @param entities 数据集合
     * @return 影响的行数
     */
    int insertBatchSomeColumn(List<T> entities);

}
