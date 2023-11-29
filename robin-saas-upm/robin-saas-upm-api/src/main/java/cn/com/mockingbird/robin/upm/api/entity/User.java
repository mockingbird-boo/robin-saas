package cn.com.mockingbird.robin.upm.api.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 用户实体
 *
 * @author zhaopeng
 * @date 2023/11/26 0:57
 **/
@Getter
@Setter
@Schema(description = "用户实体")
@TableName("upm_user")
public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "性别：1-男；2-女")
    private Integer gender;

    @Schema(description = "图像")
    private String avatar;

    @Schema(description = "状态：1-正常；2-停用；3-冻结")
    private Integer status;

    @Schema(description = "昵称")
    private String nickName;

}
