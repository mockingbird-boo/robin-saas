package cn.com.mockingbird.robin.api.model;

import lombok.Data;

/**
 * RSA 密钥对
 *
 * @author zhaopeng
 * @date 2023/11/4 15:00
 **/
@Data
public class RsaKeyPair {

    private String publicKey;

    private String privateKey;

}
