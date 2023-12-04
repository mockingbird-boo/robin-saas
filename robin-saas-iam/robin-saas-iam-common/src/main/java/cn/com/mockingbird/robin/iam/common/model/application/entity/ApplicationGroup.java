package cn.com.mockingbird.robin.iam.common.model.application.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 应用分组
 *
 * @author zhaopeng
 * @date 2023/12/4 20:58
 **/
@Schema(description = "应用组")
@Getter
@Setter
@TableName("t_application_group")
public class ApplicationGroup extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1465579651543750427L;

    @Schema(description = "应用组名称")
    private String name;

    @Schema(description = "应用组编码")
    private String code;

    @Schema(description = "应用分组类型：1-默认分组；2-自定义分组")
    private Integer type;
}
