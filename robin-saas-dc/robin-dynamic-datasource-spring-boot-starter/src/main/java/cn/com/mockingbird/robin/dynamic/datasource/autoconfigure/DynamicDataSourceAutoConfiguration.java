package cn.com.mockingbird.robin.dynamic.datasource.autoconfigure;

import cn.com.mockingbird.robin.dynamic.datasource.core.DynamicRoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * 动态数据源自动配置类
 *
 * @author zhaopeng
 * @date 2023/11/9 22:51
 **/
@AutoConfiguration(before = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceAutoConfiguration {

    @Bean("defaultDataSource")
    @ConditionalOnMissingBean(DataSource.class)
    @ConfigurationProperties("spring.datasource.hikari")
    public DataSource dataSource(DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("defaultJdbcTemplate")
    @ConditionalOnBean(name = "defaultDataSource")
    public JdbcTemplate defaultJdbcTemplate(@Qualifier("defaultDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @Primary
    public DynamicRoutingDataSource dynamicRoutingDataSource(@Qualifier("defaultDataSource") DataSource dataSource) {
        return new DynamicRoutingDataSource(dataSource);
    }

}
