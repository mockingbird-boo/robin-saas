package cn.com.mockingbird.robin.iam.common.model.account.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 组织机构成员
 *
 * @author zhaopeng
 * @date 2023/12/4 20:49
 **/
@Schema(description = "组织机构成员")
@Getter
@Setter
@TableName("t_organization_member")
public class OrganizationMember extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1531216787644972171L;

    @Schema(description = "组织机构 ID")
    private Long organizationId;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "是否主组织")
    private Boolean isPrimary;

}
