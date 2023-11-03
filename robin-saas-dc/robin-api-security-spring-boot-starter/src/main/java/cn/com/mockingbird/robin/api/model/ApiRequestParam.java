package cn.com.mockingbird.robin.api.model;

import lombok.Data;

/**
 * API 请求参数的数据模型
 *
 * @author zhaopeng
 * @date 2023/11/2 19:03
 **/
@Data
public class ApiRequestParam {

    /**
     * 请求中的加密数据
     */
    private String data;

    /**
     * 请求唯一标识
     */
    private String nonce;

    /**
     * 客户端通过 RSA 公钥加密后的 AES 密钥
     */
    private String key;

    /**
     * 数字签名
     */
    private String signature;

    /**
     * 时间戳
     */
    private String timestamp;

}
