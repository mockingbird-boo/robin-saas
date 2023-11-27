package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 角色权限实体
 *
 * @author zhaopeng
 * @date 2023/11/27 15:44
 **/
@Getter
@Setter
@Schema(description = "角色权限实体")
public class RolePermission extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1900065340733946913L;

    @Schema(description = "角色ID")
    private Long roleId;

    @Schema(description = "权限ID")
    private Long permissionId;

    @Schema(description = "权限类型")
    private Integer permissionType;
}
