package cn.com.mockingbird.robin.iam.common.model.application.dto;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 应用组 DTO
 *
 * @author zhaopeng
 * @date 2023/12/5 1:45
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class ApplicationGroupDto extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -6516657005749584186L;

    /**
     * 应用数量
     */
    private Integer appNumber;
}
