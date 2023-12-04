package cn.com.mockingbird.robin.iam.common.model.application.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 应用组关联信息查询参数
 *
 * @author zhaopeng
 * @date 2023/12/5 2:27
 **/
@Schema(description = "应用组关联信息查询参数")
@Data
public class ApplicationGroupAssociationQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -5456638743203510668L;

    @Schema(description = "应用组 ID")
    private String groupId;

    @Schema(description = "应用名称")
    private String applicationName;

}
