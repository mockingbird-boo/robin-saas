package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 客户端实体
 *
 * @author zhaopeng
 * @date 2023/11/26 1:00
 **/
@Getter
@Setter
@Schema(description = "客户端实体")
public class Client extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1769298163479672321L;

    @Schema(description = "客户端 ID")
    private String clientId;

    @Schema(description = "客户端密钥")
    private String clientSecret;

    @Schema(description = "资源 IDs")
    private String resourceIds;

    @Schema(description = "作用域")
    private String scope;

    @Schema(description = "授权方式")
    private String[] authorizedGrantTypes;

    @Schema(description = "回调地址")
    private String redirectUri;

    @Schema(description = "权限集合")
    private String authorities;

    @Schema(description = "请求令牌有效时间")
    private Integer accessTokenValidity;

    @Schema(description = "刷新令牌有效时间")
    private Integer refreshTokenValidity;

    @Schema(description = "扩展信息")
    private String extendedInformation;

    @Schema(description = "是否自动放行")
    private Boolean autoApprove;

}
