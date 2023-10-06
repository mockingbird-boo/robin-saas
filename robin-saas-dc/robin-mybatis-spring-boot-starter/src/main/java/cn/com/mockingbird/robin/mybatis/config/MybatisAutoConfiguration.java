package cn.com.mockingbird.robin.mybatis.config;

import cn.com.mockingbird.robin.mybatis.handler.PublicFieldsHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis 自动配置类
 *
 * @author zhaopeng
 * @date 2023/10/6 1:55
 **/
@Configuration
@EnableConfigurationProperties(MultiTenantProperties.class)
public class MybatisAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public PublicFieldsHandler defaultMetaObjectHandler() {
        return new PublicFieldsHandler();
    }

}
