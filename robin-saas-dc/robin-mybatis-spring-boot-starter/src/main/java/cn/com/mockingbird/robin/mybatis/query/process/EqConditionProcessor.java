package cn.com.mockingbird.robin.mybatis.query.process;

import cn.com.mockingbird.robin.mybatis.query.Condition;
import cn.com.mockingbird.robin.mybatis.query.ConditionProcessor;
import cn.com.mockingbird.robin.mybatis.query.Logic;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.annotation.Annotation;

/**
 * EQ 条件处理器
 *
 * @author zhaopeng
 * @date 2023/10/12 13:54
 **/
public class EqConditionProcessor implements ConditionProcessor {

    @Override
    public <T> void process(QueryWrapper<T> queryWrapper, String column, Object value) {
        queryWrapper.eq(column, value);
    }

    @Override
    public <T> ConditionProcessor preProcess(Annotation annotation, QueryWrapper<T> queryWrapper) {
        Condition.EQ eq = (Condition.EQ) annotation;
        if (eq.logic() == Logic.OR) {
            queryWrapper.or();
        }
        return this;
    }

}
