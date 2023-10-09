package cn.com.mockingbird.robin.mybatis.util;

import cn.com.mockingbird.robin.common.util.StringCamelUtils;
import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import cn.com.mockingbird.robin.webmvc.model.PageParams;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.ArrayUtils;
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
     * 注意：暂时仅支持 eq 查询，不支持模糊查询、范围查询等，如需要可以自己创建 QueryWrapper 实例
     * @param entity 实体类实例
     * @return QueryWrapper
     * @param <T> 实体类泛型
     */
    public static <T> QueryWrapper<T> entity2Wrapper(T entity) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        try {
            Class<?> entityClass = entity.getClass();
            Field[] fields = entityClass.getDeclaredFields();
            Class<?> entitySuperClass = entityClass.getSuperclass();
            if (entitySuperClass == BaseEntity.class) {
                Field[] declaredFields = entitySuperClass.getDeclaredFields();
                fields = ArrayUtils.addAll(fields, declaredFields);
            }
            for (Field field : fields) {
                String fieldName = field.getName();
                String columnName = StringCamelUtils.humpToUnderline(fieldName);
                TableField fieldAnnotation = field.getAnnotation(TableField.class);
                // 不存在的列或序列化字段可以忽略
                if ((fieldAnnotation != null && !fieldAnnotation.exist()) || StringUtils.equals(fieldName, "serialVersionUID")) {
                    continue;
                }
                // 如果通过 @TableField 注解标记了列名，那么以该注解标记的列名优先
                if (fieldAnnotation != null && StringUtils.isNotBlank(fieldAnnotation.value())) {
                    columnName = fieldAnnotation.value();
                }
                // 获取字段的 get 方法
                Method method = entityClass.getMethod("get" + cn.com.mockingbird.robin.common.util.StringUtils.upperTheFirstChar(fieldName));
                // 执行 get 方法获取对象字段值
                Object fieldValue = method.invoke(entity);
                if (fieldValue instanceof String stringFieldValue) {
                    queryWrapper.eq(StringUtils.isNotBlank(stringFieldValue), columnName, fieldValue);
                } else {
                    queryWrapper.eq(fieldValue != null, columnName, fieldValue);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        return queryWrapper;
    }

}
