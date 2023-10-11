package cn.com.mockingbird.robin.web.config;

import io.swagger.v3.oas.models.info.Info;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Open API 配置属性类
 *
 * @author zhaopeng
 * @date 2023/10/11 22:14
 **/
@Data
@ConfigurationProperties(prefix = "spring.open-api")
public class OpenApiConfigurationProperties {

    private Boolean enable = Boolean.FALSE;

    private final Info info = new Info();

}
