package cn.com.mockingbird.robin.iam.common.model.application.query;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 应用授权策略查询参数
 *
 * @author zhaopeng
 * @date 2023/12/5 2:29
 **/
@Schema(description = "应用授权策略查询参数")
@Data
public class ApplicationAuthorizationPolicyQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 2568743747183197432L;

    /**
     * 应用 ID
     */
    @Parameter(description = "应用 ID")
    private String applicationId;

    /**
     * 应用名称
     */
    @Parameter(description = "应用名称")
    private String applicationName;

    /**
     * 授权主体 ID
     */
    @Parameter(description = "授权主体ID")
    private String subjectId;

    /**
     * 授权主体名称
     */
    @Parameter(description = "授权主体名称")
    private String subjectName;

    /**
     * 授权主体类型
     */
    @Parameter(description = "授权主体类型：1-角色；2-用户；3-组织机构；4-用户组；5-客户端")
    private Integer subjectType;

}
