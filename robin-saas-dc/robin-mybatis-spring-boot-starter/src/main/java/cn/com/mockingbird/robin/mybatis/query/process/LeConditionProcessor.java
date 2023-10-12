package cn.com.mockingbird.robin.mybatis.query.process;

import cn.com.mockingbird.robin.mybatis.query.Condition;
import cn.com.mockingbird.robin.mybatis.query.ConditionProcessor;
import cn.com.mockingbird.robin.mybatis.query.Logic;
import cn.com.mockingbird.robin.mybatis.query.QueryCondition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.annotation.Annotation;

/**
 * LE 条件处理器
 *
 * @author zhaopeng
 * @date 2023/10/12 22:42
 **/
public class LeConditionProcessor implements ConditionProcessor {

    @Override
    public <T> void process(QueryWrapper<T> queryWrapper, QueryCondition queryCondition) {
        queryWrapper.le(queryCondition.getColumn(), queryCondition.getValue());
    }

    @Override
    public <T> ConditionProcessor preProcess(Annotation annotation, QueryWrapper<T> queryWrapper) {
        Condition.LE le = (Condition.LE) annotation;
        if (le.logic() == Logic.OR) {
            queryWrapper.or();
        }
        return this;
    }

}
