package cn.com.mockingbird.robin.iam.common.model.application.dto;

import cn.com.mockingbird.robin.iam.common.model.application.entity.ApplicationFormConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 应用 FORM 配置 DTO
 *
 * @author zhaopeng
 * @date 2023/12/5 2:07
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationFormConfigDto extends ApplicationFormConfig {

    @Serial
    private static final long serialVersionUID = 4827937064938206565L;

    /**
     * 应用编码
     */
    private String appCode;

    /**
     * 模版
     */
    private String appTemplate;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 客户端秘钥
     */
    private String clientSecret;

    /**
     * SSO 登录类型：1-仅应用发起 SSO；2-支持门户和应用发起 SSO
     */
    private Integer loginType;

    /**
     * SSO 登录链接
     */
    private String loginUrl;

    /**
     * 授权类型：1-全员可访问；2-手动授权
     */
    private Integer authorizationType;

    /**
     * 应用是否启用
     */
    private Boolean enabled;
}
