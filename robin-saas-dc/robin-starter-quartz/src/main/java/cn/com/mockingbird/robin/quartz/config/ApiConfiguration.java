package cn.com.mockingbird.robin.quartz.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * API 接口文档配置类
 *
 * @author zhaopeng
 * @date 2023/10/2 20:19
 **/
@Configuration
public class ApiConfiguration implements WebMvcConfigurer {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("Quartz API 接口文档")
                .description("支持定时任务接入、执行、管理")
                .version("1.0.0")
        );
    }

}
