package cn.com.mockingbird.robin.iam.common.model.application.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 应用账户
 *
 * @author zhaopeng
 * @date 2023/12/4 20:57
 **/
@Schema(description = "应用账户")
@Getter
@Setter
@TableName("t_application_account")
public class ApplicationAccount extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -4571949216457958262L;

    @Schema(description = "应用 ID")
    private Long applicationId;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "账户名")
    private String account;

    @Schema(description = "账户密码")
    private String password;

}
