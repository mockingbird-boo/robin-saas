package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 用户租户实体
 *
 * @author zhaopeng
 * @date 2023/11/27 14:18
 **/
@Getter
@Setter
@Schema(description = "用户租户实体")
public class UserTenant extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -3071276220226426477L;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "租户 ID")
    private Long tenantId;


}
