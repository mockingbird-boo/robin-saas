package cn.com.mockingbird.robin.mybatis.query;

import cn.com.mockingbird.robin.common.util.StringCamelUtils;
import cn.com.mockingbird.robin.mybatis.query.process.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * QueryWrapper 工具类
 *
 * @author zhaopeng
 * @date 2023/10/12 11:19
 **/
public class QueryWrapperBuilder {

    private static final Map<Class<? extends Annotation>, ConditionProcessor> PROCESSORS = new HashMap<>();

    /**
     * bean 实例转 QueryWrapper
     * 支持在 bean 类型上通过各种条件注解注明条件拼接规则
     * @see Condition
     * @see Condition.EQ 等于
     * @see Condition.NE 不等于
     * @see Condition.LIKE 模糊查找
     * @see Condition.GT 大于
     * @see Condition.LT 小于
     * @see Condition.GE 大于等于
     * @see Condition.LE 小于等于
     * @see Condition.IN IN 范围查询
     * @see Condition.BETWEEN BETWEEN 范围查询
     * @param bean bean 实例
     * @return QueryWrapper
     * @param <T> bean 泛型
     */
    public static <T> QueryWrapper<T> beanToQueryWrapper(T bean) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        final Field[] fields = bean.getClass().getDeclaredFields();
        Stream.of(fields).forEach(field -> {
            field.setAccessible(true);
            Object fieldValue;
            try {
                fieldValue = field.get(bean);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (fieldValue != null) {
                final String fieldName = field.getName();
                final String columnName = StringCamelUtils.humpToUnderline(fieldName);
                final QueryCondition queryCondition = new QueryCondition(columnName, fieldValue);
                Annotation[] annotations = field.getAnnotations();
                Arrays.stream(annotations).forEach(annotation -> {
                    final Class<? extends Annotation> annotationType = annotation.annotationType();
                    Optional.ofNullable(PROCESSORS.get(annotationType)).ifPresent(conditionProcessor ->
                            conditionProcessor.preProcess(annotation, queryWrapper).process(queryWrapper, queryCondition));
                });
            }
        });
        return queryWrapper;
    }

    static {
        PROCESSORS.put(Condition.EQ.class, new EqConditionProcessor());
        PROCESSORS.put(Condition.NE.class, new NeConditionProcessor());
        PROCESSORS.put(Condition.LIKE.class, new LikeConditionProcessor());
        PROCESSORS.put(Condition.GT.class, new GtConditionProcessor());
        PROCESSORS.put(Condition.LT.class, new LtConditionProcessor());
        PROCESSORS.put(Condition.GE.class, new GeConditionProcessor());
        PROCESSORS.put(Condition.LE.class, new LeConditionProcessor());
        PROCESSORS.put(Condition.IN.class, new InConditionProcessor());
        PROCESSORS.put(Condition.BETWEEN.class, new BetweenConditionProcessor());
    }

}
