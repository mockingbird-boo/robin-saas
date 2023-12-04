package cn.com.mockingbird.robin.iam.common.model.account.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 用户组
 *
 * @author zhaopeng
 * @date 2023/12/4 20:47
 **/
@Schema(description = "用户组")
@Getter
@Setter
@TableName("t_user_group")
public class UserGroup extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -6378824474997691638L;

    @Schema(description = "用户组名")
    private String name;

    @Schema(description = "用户组编码")
    private String code;

}
