package cn.com.mockingbird.robin.iam.common.model.application.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 应用
 *
 * @author zhaopeng
 * @date 2023/12/4 20:57
 **/
@Schema(description = "应用")
@Getter
@Setter
@TableName("t_application")
public class Application extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 5777060754309349455L;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "应用编码")
    private String code;

    @Schema(description = "客户端 ID")
    private String clientId;

    @Schema(description = "客户端密钥")
    private String clientSecret;

    @Schema(description = "模板")
    private String template;

    @Schema(description = "协议：1-OIDC；2-JWT；3-FORM；4-TSA")
    private Integer protocol;

    @Schema(description = "应用类型：1-标准应用；2-其他")
    private Integer type;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "登录类型：1-仅应用发起 SSO；2-支持门户和应用发起 SSO")
    private Integer loginType;

    @Schema(description = "SSO 登录 URL")
    private String loginUrl;

    @Schema(description = "授权类型：1-全员可访问；2-手动授权")
    private Integer authorizationType;

    @Schema(description = "是否启用：0-否；1-是")
    private Boolean enable;

}
