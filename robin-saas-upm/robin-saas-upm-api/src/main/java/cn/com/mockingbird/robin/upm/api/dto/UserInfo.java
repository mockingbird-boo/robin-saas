package cn.com.mockingbird.robin.upm.api.dto;

import cn.com.mockingbird.robin.upm.api.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 *
 * @author zhaopeng
 * @date 2023/11/26 1:56
 **/
@Data
@Schema(description = "用户信息")
public class UserInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1904246546019526475L;

    @Schema(description = "用户实例")
    private User user;

    @Schema(description = "用户角色")
    private Long[] roles;

    @Schema(description = "用户权限")
    private String[] permissions;

}
