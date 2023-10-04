package cn.com.mockingbird.robin.banner.autoconfigure;

import cn.com.mockingbird.robin.banner.core.BannerApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Banner 自动配置类
 *
 * @author zhaopeng
 * @date 2023/10/4 23:44
 **/
@Configuration
public class BannerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public BannerApplicationRunner bannerApplicationRunner() {
        return new BannerApplicationRunner();
    }

}
