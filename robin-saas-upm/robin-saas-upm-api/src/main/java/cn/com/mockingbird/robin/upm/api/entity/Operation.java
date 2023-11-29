package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 操作实体
 *
 * @author zhaopeng
 * @date 2023/11/30 0:02
 **/
@Getter
@Setter
@Schema(description = "操作实体")
public class Operation extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -790016461035363576L;

    @Schema(description = "菜单 ID")
    private Long menuId;

    @Schema(description = "操作名称")
    private String name;

    @Schema(description = "操作编码")
    private String code;

    @Schema(description = "是否可见")
    private Boolean isVisible;

}
