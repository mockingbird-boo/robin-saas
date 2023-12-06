package cn.com.mockingbird.robin.iam.common.model.authentication;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 社交身份认证提供者
 *
 * @author zhaopeng
 * @date 2023/12/4 20:58
 **/
@Schema(description = "社交身份认证提供者")
@Getter
@Setter
@TableName("t_identity_provider")
public class IdentityProvider extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 6880263278806346284L;

    @Schema(description = "认证者名称")
    private String name;

    @Schema(description = "认证者编码")
    private String code;

    @Schema(description = "认证平台")
    private String platform;

    @Schema(description = "分类")
    private String type;

    @Schema(description = "配置")
    private String config;

    @Schema(description = "是否启用")
    private Boolean enable;

    @Schema(description = "是否显示")
    private Boolean displayable;
}
