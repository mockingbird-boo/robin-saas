package cn.com.mockingbird.saas.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 链路追踪配置信息
 *
 * @author zhaopeng
 * @date 2023/8/23 1:01
 **/
@Setter
@RefreshScope
@ConfigurationProperties("spring.request")
public class RequestProperties {
    /**
     * 是否开启日志链路追踪
     */
    private Boolean enableTrace = false;
    /**
     * 是否启用获取IP地址
     */
    private Boolean enableAnalyzeIp = false;
    /**
     * 是否启用增强模式
     */
    private Boolean enableEnhance = false;

    public Boolean isTrace() {
        return this.enableEnhance;
    }

    public Boolean isAnalyzeIp() {
        return this.enableAnalyzeIp;
    }

    public boolean isEnhance() {
        return enableEnhance;
    }
}
