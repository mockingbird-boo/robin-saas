package cn.com.mockingbird.robin.mybatis.util;

import cn.com.mockingbird.robin.common.util.StringCamelUtils;
import cn.com.mockingbird.robin.webmvc.model.PageParams;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 分页工具类
 *
 * @author zhaopeng
 * @date 2023/10/8 23:45
 **/
public class MyBatisPlusUtils {

    /**
     * 构建 {@link Page} 实例
     * @param pageParams 分页请求参数
     * @return {@link Page} 实例
     * @param <T> 分页泛型
     */
    public static <T> Page<T> buildPage(PageParams pageParams) {
        return new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
    }

    /**
     * 实体类转查询包装类 QueryWrapper
     * @param entity 实体类实例
     * @return QueryWrapper
     * @param <T> 实体类泛型
     */
    public static <T> QueryWrapper<T> entity2Wrapper(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        try {
            Class<?> entityClass = entity.getClass();
            Field[] fields = entityClass.getFields();
            for (Field field : fields) {
                String name = field.getName();
                if (StringUtils.equals(name, "serialVersionUID")) {
                    continue;
                }
                String column = StringCamelUtils.humpToUnderline(name);
                TableField fieldAnnotation = field.getAnnotation(TableField.class);
                if (fieldAnnotation != null && StringUtils.isNotBlank(fieldAnnotation.value())) {
                    column = fieldAnnotation.value();
                }
                Method method = entityClass.getDeclaredMethod("get" + captureName(name), (Class<?>) null);
                Object value = method.invoke(entity);
                if (value instanceof String finalValue) {
                    queryWrapper.eq(StringUtils.isNotBlank(finalValue), column, value);
                } else {
                    queryWrapper.eq(value != null, column, value);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return queryWrapper;
    }

    private static String captureName(String str) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

}
