package cn.com.mockingbird.robin.web.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Open API 配置类
 *
 * @author zhaopeng
 * @date 2023/10/11 22:11
 **/
@AutoConfiguration(after = WebAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spring.web.enhance.open-api", name = "enable", havingValue = "true")
@EnableConfigurationProperties(OpenApiConfigurationProperties.class)
public class OpenApiConfiguration implements WebMvcConfigurer {

    @Bean
    public OpenAPI openApi(OpenApiConfigurationProperties openApiConfigurationProperties) {
        return new OpenAPI().info(openApiConfigurationProperties.getInfo());
    }

}
