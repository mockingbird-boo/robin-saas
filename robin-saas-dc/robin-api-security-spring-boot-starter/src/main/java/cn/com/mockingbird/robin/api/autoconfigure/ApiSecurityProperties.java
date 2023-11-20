package cn.com.mockingbird.robin.api.autoconfigure;

import cn.com.mockingbird.robin.api.model.RsaKeyPair;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * API 接口安全增强配置类
 *
 * @author zhaopeng
 * @date 2023/11/2 23:34
 **/
@Data
@ConfigurationProperties("spring.web.api.security")
public class ApiSecurityProperties {

    /**
     * 是否开启 API 接口安全增强
     */
    private Boolean enable = true;

    /**
     * 服务器端密钥对
     */
    private RsaKeyPair serverKeyPair;

    /**
     * 客户端密钥对
     */
    private RsaKeyPair clientKeyPair;

}
