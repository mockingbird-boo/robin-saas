package cn.com.mockingbird.robin.common.util;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Orika 封装的工具类，用于实例转换
 *
 * version 1.0.0
 * @author zhaopeng
 * @date 2023/9/21 1:59
 **/
public enum OrikaUtils {

    INSTANCE;

    private static final MapperFactory FACTORY = new DefaultMapperFactory.Builder().build();

    /**
     * 字段映射缓存
     */
    private static final Map<String, MapperFacade> MAPPER_FACADE_CACHE = new ConcurrentHashMap<>();


    /**
     * 类型转换函数
     * @param sourceInstance 源实例
     * @param targetClass 目标类
     * @param fieldMap  源类与目标类字段映射自定义
     * @param <S> 源泛型
     * @param <T> 目标泛型
     * @return 目标类实例
     */
    public  <S, T> T convert(S sourceInstance, Class<T> targetClass, Map<String, String> fieldMap) {
        if (sourceInstance == null) {
            return null;
        }
        return getMapperFacade(sourceInstance.getClass(), targetClass, fieldMap).map(sourceInstance, targetClass);
    }

    /**
     * 类型转换函数
     * @param sourceInstance 源实例
     * @param targetClass 目标类
     * @return 目标类实例
     * @param <S> 源泛型
     * @param <T> 目标泛型
     */
    public  <S, T> T convert(S sourceInstance, Class<T> targetClass) {
        return convert(sourceInstance, targetClass, null);
    }

    /**
     * 集合类型转换函数
     * @param sourceInstances 源实例集合
     * @param targetClass 目标类
     * @param fieldMap 字段映射定义
     * @return 目标类实例集合
     * @param <S> 源泛型
     * @param <T> 目标泛型
     */
    public <S, T> List<T> convertList(List<S> sourceInstances, Class<T> targetClass, Map<String, String> fieldMap) {
        if (sourceInstances == null) {
            return null;
        }
        if (sourceInstances.isEmpty()) {
            return new ArrayList<>(0);
        }
        return getMapperFacade(sourceInstances.get(0).getClass(), targetClass, fieldMap).mapAsList(sourceInstances, targetClass);
    }

    /**
     * 集合类型转换函数
     * @param sourceInstances 源实例集合
     * @param targetClass 目标类
     * @return 目标类实例集合
     * @param <S> 源泛型
     * @param <T> 目标泛型
     */
    public <S, T> List<T> convertList(List<S> sourceInstances, Class<T> targetClass) {
        return convertList(sourceInstances, targetClass, null);
    }

    /**
     * 获取自定义映射实例
     * @param source 源类
     * @param target 目标类
     * @param fieldMap 字段映射定义
     * @return 自定义映射实例
     * @param <S> 源类泛型
     * @param <T> 目标类泛型
     */
    private <S, T> MapperFacade getMapperFacade(Class<S> source, Class<T> target, Map<String, String> fieldMap) {
        String key = source.getCanonicalName() + ":" + target.getCanonicalName();
        if (MAPPER_FACADE_CACHE.containsKey(key)) {
            return MAPPER_FACADE_CACHE.get(key);
        }
        register(source, target, fieldMap);
        MapperFacade mapperFacade = FACTORY.getMapperFacade();
        MAPPER_FACADE_CACHE.put(key, mapperFacade);
        return mapperFacade;
    }

    /**
     * 映射策略注册
     * @param source 源类
     * @param target 目标类
     * @param fieldMap 字段映射定义
     * @param <S> 源类泛型
     * @param <T> 目标类泛型
     */
    public  <S, T> void register(Class<S> source, Class<T> target, Map<String, String> fieldMap){
        if (fieldMap == null || fieldMap.isEmpty()) {
            FACTORY.classMap(source, target).byDefault().register();
        } else {
            ClassMapBuilder<S, T> classMapBuilder = FACTORY.classMap(source, target);
            fieldMap.forEach(classMapBuilder::field);
            classMapBuilder.byDefault().register();
        }
    }

}
