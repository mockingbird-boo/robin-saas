package cn.com.mockingbird.robin.iam.common.model.application.dto;

import cn.com.mockingbird.robin.iam.common.model.application.entity.ApplicationAuthorizationPolicy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 应用授权策略 DTO
 *
 * @author zhaopeng
 * @date 2023/12/5 01:42
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationAuthorizationPolicyDto extends ApplicationAuthorizationPolicy {

    @Serial
    private static final long serialVersionUID = 1298759810522848698L;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 模板
     */
    private String appTemplate;

    /**
     * 协议：1-OIDC；2-JWT；3-FORM；4-TSA
     */
    private Integer appProtocol;

    /**
     * 应用类型：1-标准应用；2-其他
     */
    private Integer appType;

    /**
     * 授权主体
     */
    private String subjectName;

}
