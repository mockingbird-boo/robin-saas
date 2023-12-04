package cn.com.mockingbird.robin.iam.common.model.application.dto;

import cn.com.mockingbird.robin.iam.common.model.application.entity.ApplicationAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 应用账户 DTO
 *
 * @author zhaopeng
 * @date 2023/12/5 2:05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationAccountDto extends ApplicationAccount {

    @Serial
    private static final long serialVersionUID = 1638236227337147870L;

    /**
     * 用户名称
     */
    private String username;

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
}
