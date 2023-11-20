package cn.com.mockingbird.robin.common.util.encrypt;

import cn.com.mockingbird.robin.common.constant.Standard;
import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES 对称加密工具类
 *
 * @author zhaopeng
 * @date 2023/11/2 22:10
 **/
@SuppressWarnings("unused")
@UtilityClass
public class AesUtils {

    private final int KEY_SIZE = 128;

    /**
     * 加解密算法/工作模式/填充方式
     */
    private final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * 数据加密
     * @param data 待加密数据
     * @param key 密钥
     * @return 加密数据
     */
    public String encrypt(String data, String key) {
        try {
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return Base64Utils.encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据解密
     * @param data 加密数据
     * @param key 密钥
     * @return 解密数据
     */
    public String decrypt(String data, String key) {
        try {
            byte[] decodedData = Base64Utils.decode2Bytes(data);
            SecretKeySpec secretKeySpec = getSecretKeySpec(key);
            // 创建密码器
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] bytes = cipher.doFinal(decodedData);
            return new String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成 AES 密钥
     * @return AES 密钥
     */
    public String generateAesKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(Standard.Algorithm.AES);
            generator.init(KEY_SIZE);
            SecretKey secretKey = generator.generateKey();
            byte[] secretKeyEncoded = secretKey.getEncoded();
            return Base64Utils.encode(secretKeyEncoded);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKeySpec getSecretKeySpec(String key) throws NoSuchAlgorithmException {
        KeyGenerator generator = KeyGenerator.getInstance(Standard.Algorithm.AES);
        SecureRandom secureRandom = SecureRandom.getInstance(Standard.Algorithm.SHA1PRNG);
        secureRandom.setSeed(key.getBytes());
        generator.init(KEY_SIZE, secureRandom);
        SecretKey secretKey = generator.generateKey();
        byte[] secretKeyEncoded = secretKey.getEncoded();
        return new SecretKeySpec(secretKeyEncoded, Standard.Algorithm.AES);
    }

}
