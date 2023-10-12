package cn.com.mockingbird.robin.mybatis.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.annotation.Annotation;

/**
 * 条件处理器接口
 *
 * @author zhaopeng
 * @date 2023/10/12 12:01
 **/
public interface ConditionProcessor {

    <T> void process(QueryWrapper<T> queryWrapper, String column, Object value);

    <T> ConditionProcessor preProcess(Annotation annotation, QueryWrapper<T> queryWrapper);

}
