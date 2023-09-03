package cn.com.mockingbird.saas.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author zhaopeng
 * @date 2023/8/23 1:28
 **/
@Configuration
@EnableConfigurationProperties(RequestProperties.class)
public class RequestConfiguration {
}
