package cn.com.mockingbird.robin.common.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类工具
 *
 * @author zhaopeng
 * @date 2023/11/21 15:45
 **/
@UtilityClass
public class ClassUtils {

    /**
     * 获取接口上的泛型
     * @param object 接口实例
     * @param index 泛型索引
     * @return 接口上的泛型类型
     */
    public Class<?> getInterfaceT(Object object, int index) {
        Type[] types = object.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[index];
        Type type = parameterizedType.getActualTypeArguments()[index];
        return checkType(type, index);
    }


    /**
     * 获取类上的泛型 T
     * @param object 类实例
     * @param index 泛型索引
     * @return 类上的泛型类型
     */
    public Class<?> getClassT(Object object, int index) {
        Type type = object.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType parameterizedType) {
            Type actType = parameterizedType.getActualTypeArguments()[index];
            return checkType(actType, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a class, ParameterizedType" + ", but <" + type + "> is of type " + className);
        }
    }

    private static Class<?> checkType(Type type, int index) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType parameterizedType) {
            return checkType(parameterizedType.getActualTypeArguments()[index], index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a class, ParameterizedType" + ", but <" + type + "> is of type " + className);
        }
    }

}
