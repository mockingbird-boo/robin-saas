package cn.com.mockingbird.robin.iam.common.model.account.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 组织机构
 *
 * @author zhaopeng
 * @date 2023/12/4 20:48
 **/
@Schema(description = "组织结构")
@Getter
@Setter
@TableName("t_organization")
public class Organization extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -570325371545903756L;

    @Schema(description = "组织机构名称")
    private String name;

    @Schema(description = "组织机构编码")
    private String code;

    @Schema(description = "类型：1-集团；2-公司；3-部门；4-单位")
    private Integer type;

    @Schema(description = "上级 ID")
    private Long parentId;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "显示路径")
    private String displayPath;

    @Schema(description = "外部 ID")
    private String externalId;

    @Schema(description = "数据来源：1-系统新增；2-企业微信导入；3-钉钉导入；4- 飞书导入")
    private Integer source;

    @Schema(description = "身份源 ID")
    private Long identitySourceId;

    @Schema(description = "排序值")
    private Integer sort;

    @Schema(description = "是否是叶子节点")
    private Boolean isLeaf;

    @Schema(description = "是否启用")
    private Boolean enable;

}
