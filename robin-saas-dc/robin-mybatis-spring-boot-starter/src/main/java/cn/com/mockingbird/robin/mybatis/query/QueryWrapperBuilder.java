package cn.com.mockingbird.robin.mybatis.query;

import cn.com.mockingbird.robin.common.util.StringCamelUtils;
import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import cn.com.mockingbird.robin.mybatis.query.process.EqConditionProcessor;
import cn.com.mockingbird.robin.mybatis.query.process.NeConditionProcessor;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * QueryWrapper 工具类
 *
 * @author zhaopeng
 * @date 2023/10/12 11:19
 **/
public class QueryWrapperBuilder {

    private static final Map<Class<? extends Annotation>, ConditionProcessor> PROCESSORS = new HashMap<>();

    public static <T> QueryWrapper<T> beanToQueryWrapper(T bean) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        final Field[] fields = bean.getClass().getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = field.get(bean);
                if (fieldValue != null) {
                    final String fieldName = field.getName();
                    final String columnName = StringCamelUtils.humpToUnderline(fieldName);
                    Annotation[] annotations = field.getAnnotations();
                    for (Annotation annotation : annotations) {
                        final Class<? extends Annotation> annotationType = annotation.annotationType();
                        PROCESSORS.get(annotationType).preProcess(annotation, queryWrapper).process(queryWrapper, columnName, fieldValue);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return queryWrapper;
    }

    static {
        PROCESSORS.put(Condition.EQ.class, new EqConditionProcessor());
        PROCESSORS.put(Condition.NE.class, new NeConditionProcessor());
    }

}
