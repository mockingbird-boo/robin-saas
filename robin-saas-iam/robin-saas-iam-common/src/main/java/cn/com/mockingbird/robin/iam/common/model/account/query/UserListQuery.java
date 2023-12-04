package cn.com.mockingbird.robin.iam.common.model.account.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户列表查询条件类
 *
 * @author zhaopeng
 * @date 2023/12/4 23:33
 **/
@Schema(description = "用户列表查询条件")
@Getter
@Setter
public class UserListQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 3489727872487603285L;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "组织机构 ID")
    private Long organizationId;

    @Schema(description = "是否包含子组织")
    private Boolean includeSubOrganization;

    @Schema(description = "用户状态：1-正常；2-禁用；3-锁定；4-过期锁定；5-密码过期锁定")
    private Integer status;

    @Schema(description = "数据来源：1-系统新增；2-企业微信导入；3-钉钉导入；4- 飞书导入")
    private Integer source;

}
