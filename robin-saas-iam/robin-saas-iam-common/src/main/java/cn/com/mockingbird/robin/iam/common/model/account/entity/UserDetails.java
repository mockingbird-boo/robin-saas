package cn.com.mockingbird.robin.iam.common.model.account.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 用户详情
 *
 * @author zhaopeng
 * @date 2023/12/4 20:49
 **/
@Schema(description = "用户详情")
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("t_user_details")
public class UserDetails extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -6493752886398054646L;

    @Schema(description = "用户 ID")
    private Long userId;

    @Schema(description = "证件类型：1-身份证；2-其他")
    private Integer idType;

    @Schema(description = "证件号")
    private String idNumber;

    @Schema(description = "个人主页")
    private String homePage;

    @Schema(description = "地址")
    private String address;
}
