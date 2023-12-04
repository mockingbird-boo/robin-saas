package cn.com.mockingbird.robin.iam.common.model.application.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 应用组关联
 *
 * @author zhaopeng
 * @date 2023/12/4 23:53
 **/
@Schema(description = "应用组关联")
@Getter
@Setter
@TableName("t_application_group_association")
public class ApplicationGroupAssociation extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -1483613620185399822L;

    @Schema(description = "应用组 ID")
    private Long applicationGroupId;

    @Schema(description = "应用 ID")
    private Long applicationId;

}
