package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 租户
 *
 * @author zhaopeng
 * @date 2023/11/27 14:10
 **/
@Getter
@Setter
@Schema(description = "租户")
public class Tenant extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 6831652189301410808L;

    @Schema(description = "租户名称")
    private String name;

    @Schema(description = "租户编码")
    private String code;

    @Schema(description = "上级租户")
    private Long parentId;

}
