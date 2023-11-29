package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 菜单实体
 *
 * @author zhaopeng
 * @date 2023/11/26 1:02
 **/
@Getter
@Setter
@Schema(description = "菜单实体")
public class Menu extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 4687914558375119894L;

    @Schema(description = "应用 ID")
    private Long applicationId;

    @Schema(description = "上级菜单")
    private Long parentId;

    @Schema(description = "菜单类型：1-导航；2-菜单；")
    private Integer type;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "路由")
    private String route;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "是否可见")
    private Boolean isVisible;

}
