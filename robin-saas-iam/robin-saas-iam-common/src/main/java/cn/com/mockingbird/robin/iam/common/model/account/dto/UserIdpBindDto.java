package cn.com.mockingbird.robin.iam.common.model.account.dto;

import cn.com.mockingbird.robin.iam.common.model.account.entity.UserIdpBind;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 用户认证方式绑定记录 DTO
 *
 * @author zhaopeng
 * @date 2023/12/4 23:27
 **/
@Schema(description = "用户认证方式绑定记录 DTO")
@Getter
@Setter
public class UserIdpBindDto extends UserIdpBind {

    @Serial
    private static final long serialVersionUID = 4817424764542003549L;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "认证提供商名称")
    private String idpName;
}
