package cn.com.mockingbird.robin.iam.common.model.account.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 用户组成员
 *
 * @author zhaopeng
 * @date 2023/12/4 20:47
 **/
@Schema(description = "用户组成员")
@Getter
@Setter
@TableName("t_user_group_member")
public class UserGroupMember extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 3985873289962125440L;

    @Schema(description = "用户组 ID")
    private Long groupId;

    @Schema(description = "用户 ID")
    private Long userId;
}
