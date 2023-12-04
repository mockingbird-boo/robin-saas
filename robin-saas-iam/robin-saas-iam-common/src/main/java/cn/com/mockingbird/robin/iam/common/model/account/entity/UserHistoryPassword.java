package cn.com.mockingbird.robin.iam.common.model.account.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 用户历史密码
 *
 * @author zhaopeng
 * @date 2023/12/4 20:50
 **/
@Schema(description = "用户历史密码")
@Getter
@Setter
@TableName("t_user_history_password")
public class UserHistoryPassword extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1480844325018938316L;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "更换时间")
    private LocalDateTime changedTime;

}
