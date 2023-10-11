package cn.com.mockingbird.robin.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Open API 配置类
 *
 * @author zhaopeng
 * @date 2023/10/11 22:11
 **/
@Configuration
@EnableConfigurationProperties(OpenApiConfigurationProperties.class)
public class OpenApiConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnProperty(prefix = "spring.open-api", name = "enable", havingValue = "true")
    public OpenAPI openApi(OpenApiConfigurationProperties openApiConfigurationProperties) {
        return new OpenAPI().info(openApiConfigurationProperties.getInfo());
    }

}
