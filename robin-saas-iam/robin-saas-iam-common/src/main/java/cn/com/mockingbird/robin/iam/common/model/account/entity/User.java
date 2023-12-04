package cn.com.mockingbird.robin.iam.common.model.account.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户
 *
 * @author zhaopeng
 * @date 2023/12/4 20:42
 **/
@Schema(description = "用户实体")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_user")
public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1140698452523193258L;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "手机区号")
    private String phoneAreaCode;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "图像")
    private String avatar;

    @Schema(description = "用户状态：1-正常；2-禁用；3-锁定")
    private Integer status;

    @Schema(description = "数据来源：1-系统新增；2-企业微信导入；3-钉钉导入；4- 飞书导入")
    private Integer source;

    @Schema(description = "身份源 ID")
    private Long identitySourceId;

    @Schema(description = "邮箱验证是否有效")
    private Boolean verifiedByMail;

    @Schema(description = "手机验证是否有效")
    private Boolean verifiedByPhone;

    @Schema(description = "认证数")
    private Long authNumber;

    @Schema(description = "上次认证 IP")
    private Long lastAuthIp;

    @Schema(description = "上次认证时间")
    private LocalDateTime lastAuthTime;

    @Schema(description = "上次密码修改时间")
    private LocalDateTime lastPasswordUpdatedTime;

    @Schema(description = "扩展参数")
    private String expandParams;

    @Schema(description = "外部 ID")
    private String externalId;

    @Schema(description = "过期日")
    private LocalDate expireDay;

}
