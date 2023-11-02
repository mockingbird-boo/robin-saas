package cn.com.mockingbird.robin.api.model;

import lombok.Data;

/**
 * 有安全需求的 Api 的参数模型
 *
 * @author zhaopeng
 * @date 2023/11/2 19:03
 **/
@Data
public class ApiSecurityParam {

    /**
     * 请求唯一标识
     */
    private String nonce;

    /**
     * RSA 加密后的 AES 密钥
     */
    private String key;

    /**
     * AES 加密后的 json 数据
     */
    private String data;

    /**
     * 签名
     */
    private String signature;

    /**
     * 时间戳
     */
    private String timestamp;

}
