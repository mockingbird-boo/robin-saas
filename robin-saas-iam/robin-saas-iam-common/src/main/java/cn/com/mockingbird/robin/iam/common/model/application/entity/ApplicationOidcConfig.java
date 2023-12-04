package cn.com.mockingbird.robin.iam.common.model.application.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.Set;

/**
 * 应用 OIDC 配置
 *
 * @author zhaopeng
 * @date 2023/12/4 23:54
 **/
@Schema(description = "应用 OIDC 配置")
@Getter
@Setter
@TableName("t_application_oidc_config")
public class ApplicationOidcConfig extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -8873349762098574240L;

    @Schema(description = "应用 ID")
    private Long applicationId;

    @Schema(description = "客户端认证方式")
    private Set<String> clientAuthMethods;

    @Schema(description = "授权类型")
    private Set<String> authGrantTypes;

    @Schema(description = "响应类型")
    private Set<String> responseTypes;

    @Schema(description = "重定向 URIs")
    private Set<String> redirectUris;

    @Schema(description = "作用域")
    private Set<String> grantScopes;

    @Schema(description = "是否需要同意授权")
    private Boolean requireAuthConsent;

    @Schema(description = "是否需要 PKCE")
    private Boolean requireProofKey;

    @Schema(description = "令牌身份验签算法")
    private String tokenEndpointAuthSigningAlgorithm;

    @Schema(description = "刷新 Token生存时间（分钟）")
    private Integer refreshTokenTimeToLive;

    @Schema(description = "ID Token生存时间（分钟）")
    private Integer idTokenTimeToLive;

    @Schema(description = "访问 Token生存时间（分钟）")
    private Integer accessTokenTimeToLive;

    @Schema(description = "Id Token 签名算法")
    private String idTokenSignatureAlgorithm;

    @Schema(description = "Access Token 格式")
    private String accessTokenFormat;

    @Schema(description = "是否重用刷新令牌")
    private Boolean reuseRefreshToken;

}
