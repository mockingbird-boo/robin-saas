package cn.com.mockingbird.robin.iam.common.model.application.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 应用 FORM 配置
 *
 * @author zhaopeng
 * @date 2023/12/4 23:51
 **/
@Schema(description = "应用 FORM 配置")
@Getter
@Setter
@TableName("t_application_form_config")
public class ApplicationFormConfig extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2394641437699650127L;

    @Schema(description = "应用 ID")
    private Long applicationId;

    @Schema(description = "login URL")
    private String loginUrl;

    @Schema(description = "账户名字段名")
    private String usernameField;

    @Schema(description = "账户密码字段名")
    private String passwordField;

    @Schema(description = "账户密码加密类型：1-AES；2-MD5；3-BASE64")
    private Integer passwordEncryptType;

    @Schema(description = "账户密码加密密钥")
    private String passwordEncryptKey;

    @Schema(description = "账户名加密类型：1-AES；2-MD5；3-BASE64")
    private Integer usernameEncryptType;

    @Schema(description = "账户名加密密钥")
    private String usernameEncryptKey;

    @Schema(description = "登录提交方式：1-GET；2-POST")
    private Integer submitMethod;

    @Schema(description = "表单其他信息")
    private List<OtherField> otherFields;

    @SuppressWarnings("serial")
    @Data
    @Schema(description = "表单其他信息")
    static class OtherField implements Serializable {
        private String fieldName;
        private String fieldValue;
    }

}
