package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 权限菜单实体
 *
 * @author zhaopeng
 * @date 2023/11/29 23:27
 **/
@Getter
@Setter
@Schema(description = "权限菜单实体")
public class PermissionMenu extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -8342349316971075174L;

    @Schema(description = "权限 ID")
    private Long permissionId;

    @Schema(description = "菜单 ID")
    private Long menuId;

}
