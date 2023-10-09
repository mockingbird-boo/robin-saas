package cn.com.mockingbird.robin.mybatis.config;

import cn.com.mockingbird.robin.mybatis.handler.MultiTenantHandler;
import cn.com.mockingbird.robin.mybatis.handler.PublicFieldsHandler;
import cn.com.mockingbird.robin.mybatis.injector.EnhancedSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
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
public class EnhancedMybatisAutoConfiguration {

    /**
     * 注册公共字段处理器
     * @return PublicFieldsHandler 实例
     */
    @Bean
    @ConditionalOnMissingBean
    public PublicFieldsHandler defaultMetaObjectHandler() {
        return new PublicFieldsHandler();
    }

    /**
     * 注册增强的 SQL 注入器
     * @return EnhancedSqlInjector 实例
     */
    @Bean
    @ConditionalOnMissingBean
    public EnhancedSqlInjector enhancedSqlInjector() {
        return new EnhancedSqlInjector();
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(MultiTenantProperties properties) {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        if (properties.getEnable()) {
            // 多租户插件（官方文档要求添加在分页插件前面）
            mybatisPlusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new MultiTenantHandler(properties)));
        }
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

}
