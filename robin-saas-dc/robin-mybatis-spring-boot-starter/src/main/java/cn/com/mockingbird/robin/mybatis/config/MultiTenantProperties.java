package cn.com.mockingbird.robin.mybatis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

/**
 * 多租户属性配置
 *
 * @author zhaopeng
 * @date 2023/10/6 23:05
 **/
@ConfigurationProperties(prefix = "spring.mapper.multi-tenant")
@Data
public class MultiTenantProperties {

    /**
     * 是否开启多租户功能
     */
    private Boolean enable = Boolean.FALSE;

    /**
     * 忽略的数据表
     */
    private Set<String> ignoredTables = Collections.emptySet();

    /**
     * 忽略的请求，例如登录接口这时候还不知道是租户信息
     */
    private Set<String> ignoredUrls = Collections.emptySet();

}
