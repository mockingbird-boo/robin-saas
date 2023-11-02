package cn.com.mockingbird.robin.api.autoconfigure;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * API 安全接口自动配置类
 *
 * @author zhaopeng
 * @date 2023/11/2 23:45
 **/
@AutoConfiguration
@EnableConfigurationProperties(ApiSecurityProperties.class)
public class ApiSecurityAutoConfiguration {
}
