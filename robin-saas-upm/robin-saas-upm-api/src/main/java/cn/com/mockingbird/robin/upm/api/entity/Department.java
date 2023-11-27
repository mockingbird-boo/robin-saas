package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 部门实体
 *
 * @author zhaopeng
 * @date 2023/11/26 0:59
 **/
@Getter
@Setter
@Schema(description = "部门实体")
public class Department extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -8907255857037800757L;

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "上级部门")
    private Long parentId;

}
