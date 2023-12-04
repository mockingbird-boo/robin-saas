package cn.com.mockingbird.robin.iam.common.model.application.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 应用 JWT 配置
 *
 * @author zhaopeng
 * @date 2023/12/4 23:54
 **/
@Schema(description = "应用 JWT 配置")
@Getter
@Setter
@TableName("t_application_jwt_config")
public class ApplicationJwtConfig extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 4020105441767007035L;

    @Schema(description = "应用 ID")
    private Long applicationId;

    /**
     * 业务系统中的 JWT SSO 地址，
     * 在单点登录时本系统将向该地址发送 id_token 信息，参数名为 id_token，
     * 业务系统通过 id_token 与 Public Key 可获取系统中的用户信息，
     * 如果在业务系统（SP）发起登录，请求 SP 登录地址时如果携带 service 参数，
     * 系统会检验合法性，成功后会将浏览器重定向到该地址，并携带 id_token 身份令牌。
     */
    @Schema(description = "重定向 URL")
    private String redirectUrl;

    /**
     * 业务系统中在 JWT SSO 成功后重定向的 URL，一般用于跳转到二级菜单等，
     * 若设置了该 URL，在 JWT SSO 时会以参数 target_uri 优先传递该值，
     * 若未设置该值，此时若 SSO 中有请求参数 target_uri，则会按照请求参数传递该值。此项可选。
     */
    @Schema(description = "目标 URL")
    private String targetLinkUrl;

    @Schema(description = "跳转方式：1-重定向；2-页面请求")
    private Integer bindingType;

    @Schema(description = "ID TOKEN 主体类型：1-用户名；2-应用账户")
    private Integer idTokenSubjectType;

    @Schema(description = "令牌过期时间")
    private Integer idTokenTimeToLive;
}
