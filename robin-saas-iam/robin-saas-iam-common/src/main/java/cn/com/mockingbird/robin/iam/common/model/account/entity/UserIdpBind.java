package cn.com.mockingbird.robin.iam.common.model.account.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 用户认证方式绑定记录
 *
 * @author zhaopeng
 * @date 2023/12/4 20:51
 **/
@Schema(description = "用户认证方式绑定记录")
@Getter
@Setter
@TableName("t_user_idp_bind")
public class UserIdpBind extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1423305674475816464L;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "OPEN ID")
    private String openId;

    @Schema(description = "IDP ID")
    private String idpId;

    @Schema(description = "IDP 类型")
    private Integer idpType;

    @Schema(description = "绑定时间")
    private LocalDateTime bindTime;

    @Schema(description = "附加信息")
    private String additionInfo;

}
