package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 权限
 *
 * @author zhaopeng
 * @date 2023/11/26 1:01
 **/
@Getter
@Setter
@Schema(description = "权限实体")
public class Permission extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -4958148414256383414L;

    @Schema(description = "权限编码")
    private String code;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限类型：1-应用菜单；2-功能操作；3-应用服务")
    private Integer type;

}
