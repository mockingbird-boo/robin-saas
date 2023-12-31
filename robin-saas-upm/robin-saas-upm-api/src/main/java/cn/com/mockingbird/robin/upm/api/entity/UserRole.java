package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 用户角色
 *
 * @author zhaopeng
 * @date 2023/11/26 1:00
 **/
@Getter
@Setter
@Schema(description = "用户角色实体")
public class UserRole extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -4715416343422379142L;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "角色 ID")
    private Long roleId;

}
