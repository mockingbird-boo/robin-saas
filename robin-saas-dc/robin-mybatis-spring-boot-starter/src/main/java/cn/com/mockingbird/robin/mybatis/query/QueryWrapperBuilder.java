package cn.com.mockingbird.robin.mybatis.query;

import cn.com.mockingbird.robin.common.util.StringCamelUtils;
import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * QueryWrapper 工具类
 *
 * @author zhaopeng
 * @date 2023/10/12 11:19
 **/
public class QueryWrapperBuilder {

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
                        // TODO
                        if (Condition.EQ.class == annotationType) {
                            Condition.EQ eq = (Condition.EQ) annotation;
                            processLogicCondition(eq.logic(), queryWrapper);
                            queryWrapper.eq(columnName, fieldValue);
                        }
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return queryWrapper;
    }

    private static <T> void processLogicCondition(Logic logic, QueryWrapper<T> queryWrapper) {
        if (logic == Logic.OR) {
            queryWrapper.or();
        }
    }

}
