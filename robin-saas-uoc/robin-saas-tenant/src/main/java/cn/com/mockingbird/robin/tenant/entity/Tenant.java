package cn.com.mockingbird.robin.tenant.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 租户
 *
 * @author zhaopeng
 * @date 2023/10/21 22:07
 **/
@Getter
@Setter
@TableName("uoc_tenant")
@Schema(name = "Tenant", title = "租户信息")
public class Tenant extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -2129411528421343696L;

    @Schema(title = "租户名称")
    private String name;

    @Schema(title = "租户编码")
    private String code;

    @Schema(title = "租户类型")
    private String type;

    @Schema(title = "上级租户ID")
    private Long parentId;

    @Schema(title = "状态")
    private Integer status;

    @Schema(title = "过期时间")
    private LocalDateTime expireTime;

}
