package cn.com.mockingbird.robin.mybatis.query.process;

import cn.com.mockingbird.robin.mybatis.query.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.annotation.Annotation;

/**
 * BETWEEN 条件处理器
 *
 * @author zhaopeng
 * @date 2023/10/12 23:06
 **/
public class BetweenConditionProcessor implements ConditionProcessor {

    @Override
    public <T> void process(QueryWrapper<T> queryWrapper, QueryCondition queryCondition) {
        String column = queryCondition.getColumn();
        Range<?> range = (Range<?>) queryCondition.getValue();
        queryWrapper.between(column, range.getLower(), range.getUpper());
    }

    @Override
    public <T> ConditionProcessor preProcess(Annotation annotation, QueryWrapper<T> queryWrapper) {
        Condition.BETWEEN between = (Condition.BETWEEN) annotation;
        if (between.logic() == Logic.OR) {
            queryWrapper.or();
        }
        return this;
    }

}
