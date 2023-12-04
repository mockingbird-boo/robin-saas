package cn.com.mockingbird.robin.iam.common.model.account.dto;

import cn.com.mockingbird.robin.iam.common.model.account.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 用户 DTO
 *
 * @author zhaopeng
 * @date 2023/12/4 23:24
 **/
@Schema(description = "用户 DTO")
@Getter
@Setter
public class UserDto extends User {

    @Serial
    private static final long serialVersionUID = -6082229958572956277L;

    @Schema(description = "组织机构路径")
    private String organizationPath;

    @Schema(description = "主组织机构路径")
    private String primaryOrganizationPath;

}
