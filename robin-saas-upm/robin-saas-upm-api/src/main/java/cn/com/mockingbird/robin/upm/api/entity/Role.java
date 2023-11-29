package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 角色实体
 *
 * @author zhaopeng
 * @date 2023/11/26 0:58
 **/
@Getter
@Setter
@Schema(description = "角色实体")
public class Role extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -2508420932986360055L;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "上级角色")
    private Long parentId;

    @Schema(description = "角色状态：1-正常；2-停用")
    private Integer status;

    @Schema(description = "排序号")
    private Integer sort;

}
