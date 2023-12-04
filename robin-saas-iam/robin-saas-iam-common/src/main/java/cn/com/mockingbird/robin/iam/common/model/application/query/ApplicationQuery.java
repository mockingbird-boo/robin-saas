package cn.com.mockingbird.robin.iam.common.model.application.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 应用查询参数类
 *
 * @author zhaopeng
 * @date 2023/12/5 2:09
 **/
@Schema(description = "应用查询参数")
@Data
public class ApplicationQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 5487079797865214621L;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "协议：1-OIDC；2-JWT；3-FORM；4-TSA")
    private Integer protocol;

    @Schema(description = "应用组ID")
    private Long groupId;

}
