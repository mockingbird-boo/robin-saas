package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 岗位
 *
 * @author zhaopeng
 * @date 2023/11/29 19:41
 **/
@Getter
@Setter
@Schema(description = "岗位实体")
public class Post extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -5387519894817623639L;

    @Schema(description = "岗位编码")
    private String code;

    @Schema(description = "岗位名称")
    private String name;

    @Schema(description = "岗位描述")
    private String description;

    @Schema(description = "排序号")
    private Integer sort;

}
