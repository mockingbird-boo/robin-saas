package cn.com.mockingbird.robin.api.enums;

/**
 * 客户端使用的加密算法枚举
 *
 * @author zhaopeng
 * @date 2023/11/3 19:12
 **/
@SuppressWarnings("unused")
public enum EncryptAlgorithm {
    /**
     * RSA 公钥加密数据
     */
    RSA,
    /**
     * RSA 公钥加密 AES 密钥，AES 密钥加密数据
     */
    RSA_AES
}
