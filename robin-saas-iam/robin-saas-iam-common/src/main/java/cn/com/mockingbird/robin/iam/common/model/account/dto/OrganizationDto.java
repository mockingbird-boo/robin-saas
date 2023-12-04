package cn.com.mockingbird.robin.iam.common.model.account.dto;

import cn.com.mockingbird.robin.iam.common.model.account.entity.Organization;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * 组织机构 DTO
 *
 * @author zhaopeng
 * @date 2023/12/4 23:30
 **/
@Schema(description = "组织机构 DTO")
@Getter
@Setter
public class OrganizationDto extends Organization {

    @Serial
    private static final long serialVersionUID = 5542297553920911359L;

    @Schema(description = "备注")
    private String remark;
}
