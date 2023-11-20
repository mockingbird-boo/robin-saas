package cn.com.mockingbird.robin.mybatis.config;

import cn.com.mockingbird.robin.mybatis.handler.DataEncryptHandler;
import cn.com.mockingbird.robin.mybatis.handler.MultiTenantHandler;
import cn.com.mockingbird.robin.mybatis.handler.PublicFieldsHandler;
import cn.com.mockingbird.robin.mybatis.injector.EnhancedSqlInjector;
import cn.com.mockingbird.robin.mybatis.security.AesEncryptor;
import cn.com.mockingbird.robin.mybatis.security.Algorithm;
import cn.com.mockingbird.robin.mybatis.security.Base64Encryptor;
import cn.com.mockingbird.robin.mybatis.security.DataEncryptor;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.IllegalSQLInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * Mybatis 自动配置类
 *
 * @author zhaopeng
 * @date 2023/10/6 1:55
 **/
@AutoConfiguration(after = MybatisPlusAutoConfiguration.class)
@EnableConfigurationProperties(EnhancedMybatisProperties.class)
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
    public MybatisPlusInterceptor mybatisPlusInterceptor(EnhancedMybatisProperties properties) {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        if (properties.getEnableMultiTenant()) {
            // 多租户插件（官方文档要求添加在分页插件前面）
            mybatisPlusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new MultiTenantHandler(properties)));
        }
        if (properties.getEnableOptimisticLock()) {
            // 乐观锁插件
            mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        }
        if (properties.getEnableIllegalSqlIntercept()) {
            // 非法 SQL 拦截插件
            mybatisPlusInterceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        }
        // 分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }

    @Bean
    @ConditionalOnMissingBean(DataEncryptor.class)
    @ConditionalOnProperty(prefix = "spring.mybatis.data-encrypt", name = "enable", havingValue = "true")
    public DataEncryptor dataEncryptor(EnhancedMybatisProperties enhancedMybatisProperties) {
        Algorithm algorithm = enhancedMybatisProperties.getDataEncrypt().getAlgorithm();
        return switch (algorithm) {
            case BASE64 -> new Base64Encryptor();
            case AES -> new AesEncryptor(enhancedMybatisProperties);
        };
    }

    @Bean
    public DataEncryptHandler<?> encryptHandler() {
        return new DataEncryptHandler<>();
    }

}
