package cn.com.mockingbird.robin.iam.common.model.application.entity;

import cn.com.mockingbird.robin.mybatis.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * 应用证书
 *
 * @author zhaopeng
 * @date 2023/12/4 23:51
 **/
@Schema(description = "应用证书")
@Getter
@Setter
@TableName("t_application_certificate")
public class ApplicationCertificate extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 2846255242197311066L;

    @Schema(description = "应用 ID")
    private Long applicationId;

    @Schema(description = "证书序列号")
    private BigInteger serial;

    @Schema(description = "主体")
    private String subject;

    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "有效天数")
    private Integer validity;

    @Schema(description = "算法")
    private String algorithm;

    @Schema(description = "私钥长度")
    private Integer privateKeyLength;

    @Schema(description = "私钥")
    private String privateKey;

    @Schema(description = "公钥")
    private String publicKey;

    @Schema(description = "证书")
    private String certificate;

    @Schema(description = "用途：1-OIDC JWK；2-JWT 加密")
    private Integer using;
}
