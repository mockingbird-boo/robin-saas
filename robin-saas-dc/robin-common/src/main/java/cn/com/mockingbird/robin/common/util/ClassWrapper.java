package cn.com.mockingbird.robin.common.util;

import lombok.Getter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 类处理工具
 *
 * @author zhaopeng
 * @date 2023/11/21 17:05
 **/
@SuppressWarnings("unused")
@Getter
public class ClassWrapper<T> {

    private Type type;

    private Type parameterizedType;

    public ClassWrapper() {
        processClassT(this, 0);
    }

    public void processClassT(Object object, int index) {
        Type type = getType(object);
        if (type instanceof ParameterizedType pt) {
            Type actType = pt.getActualTypeArguments()[index];
            checkType(actType, index);
            return;
        }
        String className = type == null ? "null" : type.getClass().getName();
        throw new IllegalArgumentException("Expected a class, ParameterizedType" + ", but <" + type + "> is of type " + className);
    }

    private Type getType(Object object) {
        return object.getClass().getGenericSuperclass();
    }

    private Class<?> checkType(Type type, int index) {
        if (type instanceof Class<?>) {
            this.type = type;
            return (Class<?>) type;
        }

        if (type instanceof ParameterizedType pt) {
            Type t = pt.getActualTypeArguments()[index];
            this.parameterizedType = pt;
            return checkType(t, index);
        }

        String className = type == null ? "null" : type.getClass().getName();
        throw new IllegalArgumentException("Expected a class, ParameterizedType" + ", but <" + type + "> is of type " + className);
    }

}
