package cn.com.mockingbird.robin.mybatis.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 条件处理器
 *
 * @author zhaopeng
 * @date 2023/10/12 12:01
 **/
public interface ConditionProcessor<T> {

    QueryWrapper<T> process(QueryWrapper<T> queryWrapper, String column, Object value);

}
