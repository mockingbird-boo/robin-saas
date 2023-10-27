package cn.com.mockingbird.robin.common.util.encrypt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * RSA 加解密以及签名测试
 *
 * @author zhaopeng
 * @date 2023/10/27 21:13
 **/
class RsaUtilsTest {

    @BeforeEach
    void setUp() {
        System.out.println("-------------- 测试 Start ---------------");
    }

    @AfterEach
    void tearDown() {
        System.out.println("-------------- 测试 End ---------------");
    }

    @Test
    void testRsa() {
        // 生成密钥对
        KeyPair keyPair = RsaUtils.getKeyPair();
        // 获取密钥对中的私钥字符串
        String privateKey = RsaUtils.getPrivateKey(keyPair.getPrivate());
        // 获取密钥对中的公钥字符串
        String publicKey = RsaUtils.getPublicKey(keyPair.getPublic());

        System.out.println("公钥：" + publicKey);
        System.out.println("私钥：" + privateKey);

        // 待加密数据
        String data = "兄弟，29号凌晨2点，你跟我去炸毁鬼子炮楼！";
        System.out.printf("需要进行加密的数据: %s%n", data);

        // 根据私钥字符串获取
        PublicKey pubKey;
        PrivateKey priKey;
        try {
            pubKey = RsaUtils.getPublicKey(publicKey);
            priKey = RsaUtils.getPrivateKey(privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 测试公钥加密私钥解密
        String encryptedData = RsaUtils.encrypt(data, pubKey);
        System.out.println("公钥加密后的数据：" + encryptedData);
        String decryptedData = RsaUtils.decrypt(encryptedData, priKey);
        System.out.println("私钥解密后的数据：" + decryptedData);

        // 测试私钥签名公钥验签
        String sign = RsaUtils.sign(decryptedData, priKey);
        System.out.println("私钥签名后的数据：" + sign);
        System.out.println("公钥验签是否通过：" + RsaUtils.isOriginalSign(decryptedData, pubKey, sign));
        System.out.println("公钥验签是否通过：" + RsaUtils.isOriginalSign(decryptedData + "a", pubKey, sign));
    }
}