package cn.com.mockingbird.robin.iam.common.model.account.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户组成员列表查询条件类
 *
 * @author zhaopeng
 * @date 2023/12/4 23:43
 **/
@Schema(description = "用户组成员列表查询条件类")
@Getter
@Setter
public class UserGroupMemberListQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -8979518598370975371L;

    @Schema(description = "用户组 ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

}
