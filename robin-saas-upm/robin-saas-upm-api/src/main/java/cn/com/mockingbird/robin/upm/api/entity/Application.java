package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 应用实体
 *
 * @author zhaopeng
 * @date 2023/11/29 21:03
 **/
@Getter
@Setter
@Schema(description = "应用实体")
public class Application extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 6837509492388621256L;

    @Schema(description = "上级应用")
    private Long parentId;

    @Schema(description = "应用编码")
    private String code;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "应用入口")
    private String entrance;

    @Schema(description = "应用图标")
    private String icon;

    @Schema(description = "应用类型：1-基础应用；2-共享用用；3-增值应用")
    private Integer type;

    @Schema(description = "服务模式：1-2B；2-2C")
    private Integer mode;

    @Schema(description = "应用描述")
    private String description;

}
