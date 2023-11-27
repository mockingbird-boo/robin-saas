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

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "上级菜单")
    private Long parentId;

}
