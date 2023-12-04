package cn.com.mockingbird.robin.iam.common.model.application.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 应用授权策略
 *
 * @author zhaopeng
 * @date 2023/12/4 23:49
 **/
@Schema(description = "应用授权策略")
@Getter
@Setter
@TableName("t_application_authorization_policy")
public class ApplicationAuthorizationPolicy extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -4048330010302426107L;

    @Schema(description = "应用 ID")
    private Long applicationId;

    @Schema(description = "主体 ID")
    private String subjectId;

    @Schema(description = "主体类型：1-角色；2-用户；3-组织机构；4-用户组；5-客户端")
    private Integer subjectType;

}
