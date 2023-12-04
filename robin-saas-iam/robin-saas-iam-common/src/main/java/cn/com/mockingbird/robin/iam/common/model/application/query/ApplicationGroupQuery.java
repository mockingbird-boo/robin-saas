package cn.com.mockingbird.robin.iam.common.model.application.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 应用组查询参数
 *
 * @author zhaopeng
 * @date 2023/12/5 2:19
 **/
@Schema(description = "应用分组查询参数")
@Data
public class ApplicationGroupQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 2925632744329853701L;

    @Schema(description = "应用分组名称")
    private String name;

    @Schema(description = "应用分组编码")
    private String code;

    @Schema(description = "应用分组类型：1-默认分组；2-自定义分组")
    private Integer type;

}
