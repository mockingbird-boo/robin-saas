package cn.com.mockingbird.robin.mybatis.query.process;

import cn.com.mockingbird.robin.mybatis.query.Condition;
import cn.com.mockingbird.robin.mybatis.query.ConditionProcessor;
import cn.com.mockingbird.robin.mybatis.query.Logic;
import cn.com.mockingbird.robin.mybatis.query.QueryCondition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.annotation.Annotation;

/**
 * LIKE 条件处理器
 *
 * @author zhaopeng
 * @date 2023/10/12 22:35
 **/
public class LikeConditionProcessor implements ConditionProcessor {

    @Override
    public <T> void process(QueryWrapper<T> queryWrapper, QueryCondition queryCondition) {
        queryWrapper.like(queryCondition.getColumn(), queryCondition.getValue());
    }

    @Override
    public <T> ConditionProcessor preProcess(Annotation annotation, QueryWrapper<T> queryWrapper) {
        Condition.LIKE like = (Condition.LIKE) annotation;
        if (like.logic() == Logic.OR) {
            queryWrapper.or();
        }
        return this;
    }

}
