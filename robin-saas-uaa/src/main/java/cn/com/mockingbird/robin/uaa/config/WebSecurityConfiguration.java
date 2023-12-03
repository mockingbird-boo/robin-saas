package cn.com.mockingbird.robin.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Web 安全配置类
 *
 * @author zhaopeng
 * @date 2023/11/30 18:02
 **/
@EnableWebSecurity
public class WebSecurityConfiguration {

    /**
     * 默认的安全策略
     */
//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) {
//        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {})
//    }

}
