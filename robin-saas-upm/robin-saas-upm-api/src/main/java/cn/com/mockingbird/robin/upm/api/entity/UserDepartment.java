package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 用户部门实体
 *
 * @author zhaopeng
 * @date 2023/11/27 15:48
 **/
@Getter
@Setter
@Schema(description = "用户部门实体")
public class UserDepartment extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 8584800105988906644L;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "部门 ID")
    private Long departmentId;

}
