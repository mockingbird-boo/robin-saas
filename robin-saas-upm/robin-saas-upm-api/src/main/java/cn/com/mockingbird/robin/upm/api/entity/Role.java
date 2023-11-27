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

    @Schema(description = "角色名")
    private String name;

    @Schema(description = "角色编码")
    private String code;

}
