package cn.com.mockingbird.robin.iam.common.model.application.query;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 应用账户查询参数
 *
 * @author zhaopeng
 * @date 2023/12/5 2:23
 **/
@Schema(description = "应用账户查询参数")
@Data
public class ApplicationAccountQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1597163924104085258L;

    /**
     * 应用 ID
     */
    @Parameter(description = "applicationId")
    private String applicationId;
    /**
     * 用户 ID
     */
    @Parameter(description = "用户 ID")
    private String userId;
    /**
     * 用户名
     */
    @Parameter(description = "用户名")
    private String username;
    /**
     * 账户名称
     */
    @Parameter(description = "账户名称")
    private String account;

    /**
     * 应用名称
     */
    @Parameter(description = "应用名称")
    private String applicationName;
}
