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

    <T> void process(QueryWrapper<T> queryWrapper, QueryCondition queryCondition);

    /**
     * 前置处理
     * 由子处理器根据注解的 Logic 值处理逻辑运算
     * @param annotation 注解
     * @param queryWrapper 查询包装器
     * @return 条件处理器
     * @param <T> 查询包装器泛型
     */
    <T> ConditionProcessor preProcess(Annotation annotation, QueryWrapper<T> queryWrapper);

}
