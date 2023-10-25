package cn.com.mockingbird.robin.common.util.encrypt;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * RSA 加密工具
 *
 * @author zhaopeng
 * @date 2023/10/26 0:58
 **/
@SuppressWarnings("unused")
@UtilityClass
public class RsaUtils {

    /**
     * 生成密钥对并写入文件
     * @param algorithm 非对称加密算法
     * @param privateKeyPath 私钥路径
     * @param publicKeyPath 公钥路径
     * @throws NoSuchAlgorithmException 算法参数异常
     * @throws IOException 文件IO异常
     */
    public void generateFile(String algorithm, String privateKeyPath, String publicKeyPath) throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String base64PrivateKey = Base64Utils.encode(privateKey.getEncoded());
        String base64PublicKey = Base64Utils.encode(publicKey.getEncoded());
        FileUtils.writeStringToFile(new File(privateKeyPath), base64PrivateKey, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(new File(publicKeyPath), base64PublicKey, StandardCharsets.UTF_8);
    }


}
