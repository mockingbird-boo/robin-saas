package cn.com.mockingbird.robin.iam.support.security.util;

import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * HttpSecurity 工具类
 *
 * @author zhaopeng
 * @date 2023/12/6 0:37
 **/
@UtilityClass
public class HttpSecurityConfigUtils {

    /**
     * 获取 PasswordEncoder 实例
     * @param httpSecurity HttpSecurity 实例
     * @return PasswordEncoder 实例
     */
    public PasswordEncoder getPasswordEncoder(HttpSecurity httpSecurity) {
        PasswordEncoder passwordEncoder = httpSecurity.getSharedObject(PasswordEncoder.class);
        if (passwordEncoder == null) {
            passwordEncoder = getOptionalBean(httpSecurity, PasswordEncoder.class);
            httpSecurity.setSharedObject(PasswordEncoder.class, passwordEncoder);
        }

        return passwordEncoder;
    }

    /**
     * 获取 UserDetailsService 实例
     * @param httpSecurity HttpSecurity 实例
     * @return UserDetailsService 实例
     */
    public UserDetailsService getUserDetailsService(HttpSecurity httpSecurity) {
        UserDetailsService userDetailsService = httpSecurity.getSharedObject(UserDetailsService.class);
        if (userDetailsService == null) {
            userDetailsService = getOptionalBean(httpSecurity, UserDetailsService.class);
            httpSecurity.setSharedObject(UserDetailsService.class, userDetailsService);
        }

        return userDetailsService;
    }

    /**
     * 获取可选的 Bean
     * @param httpSecurity HttpSecurity 实例
     * @param type 参数类型
     * @return 可选的 Bean
     * @param <T> 参数类型的泛型
     */
    public <T> T getOptionalBean(HttpSecurity httpSecurity, Class<T> type) {
        Map<String, T> beansMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(httpSecurity.getSharedObject(ApplicationContext.class), type);
        if (beansMap.size() > 1) {
            int size = beansMap.size();
            String typeName = type.getName();
            throw new NoUniqueBeanDefinitionException(type, size, "Expected single matching bean of type '" + typeName + "' but found " + beansMap.size() + ": " + StringUtils.collectionToCommaDelimitedString(beansMap.keySet()));
        } else {
            return !beansMap.isEmpty() ? beansMap.values().iterator().next() : null;
        }
    }

    /**
     * 根据 ResolvableType 获取可选的 Bean
     * @param httpSecurity HttpSecurity 实例
     * @param type Java 类型的封装
     * @return 可选的 Bean
     */
    public Object getOptionalBean(HttpSecurity httpSecurity, ResolvableType type) {
        ApplicationContext context = httpSecurity.getSharedObject(ApplicationContext.class);
        String[] names = context.getBeanNamesForType(type);
        if (names.length > 1) {
            throw new NoUniqueBeanDefinitionException(type, names);
        } else {
            return names.length == 1 ? context.getBean(names[0]) : null;
        }
    }

}
