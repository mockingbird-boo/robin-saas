package cn.com.mockingbird.robin.iam.support.security.util;

import jakarta.servlet.Filter;
import lombok.experimental.UtilityClass;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * HttpSecurityFilterOrderRegistration 工具类
 *
 * @author zhaopeng
 * @date 2023/12/6 0:37
 **/
@SuppressWarnings("all")
@UtilityClass
public class HttpSecurityFilterOrderRegistrationUtils {
    
    private final String FILTER_ORDER_REGISTRATION_CLASS = "org.springframework.security.config.annotation.web.builders.FilterOrderRegistration";
    
    public void putFilterAfter(HttpSecurity httpSecurity, Filter filter, Class<? extends Filter> afterFilter) {
        try {
            Class<?> filterOrderRegistrationClass = Class.forName(FILTER_ORDER_REGISTRATION_CLASS);
            Method put = filterOrderRegistrationClass.getDeclaredMethod("put", Class.class, Integer.TYPE);
            put.setAccessible(true);
            Method getOrder = filterOrderRegistrationClass.getDeclaredMethod("getOrder", Class.class);
            getOrder.setAccessible(true);
            Field filterOrders = httpSecurity.getClass().getDeclaredField("filterOrders");
            filterOrders.setAccessible(true);
            Object filterOrderRegistration = filterOrders.get(httpSecurity);
            Integer order = (Integer)getOrder.invoke(filterOrderRegistration, afterFilter);
            put.invoke(filterOrderRegistration, filter.getClass(), order + 1);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException var9) {
            throw new RuntimeException(var9);
        }
    }

    public void putFilterBefore(HttpSecurity httpSecurity, Filter filter, Class<? extends Filter> beforeFilter) {
        try {
            Class<?> filterOrderRegistrationClass = Class.forName(FILTER_ORDER_REGISTRATION_CLASS);
            Method put = filterOrderRegistrationClass.getDeclaredMethod("put", Class.class, Integer.TYPE);
            put.setAccessible(true);
            Method getOrder = filterOrderRegistrationClass.getDeclaredMethod("getOrder", Class.class);
            getOrder.setAccessible(true);
            Field filterOrders = httpSecurity.getClass().getDeclaredField("filterOrders");
            filterOrders.setAccessible(true);
            Object filterOrderRegistration = filterOrders.get(httpSecurity);
            Integer order = (Integer)getOrder.invoke(filterOrderRegistration, beforeFilter);
            put.invoke(filterOrderRegistration, filter.getClass(), order - 1);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException | ClassNotFoundException var9) {
            throw new RuntimeException(var9);
        }
    }

}
