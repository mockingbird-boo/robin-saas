package cn.com.mockingbird.robin.iam.support.util;

import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES 加密工具
 *
 * @author zhaopeng
 * @date 2023/12/7 21:04
 **/
public class AesUtils {

    private static final String ALGORITHM = "AES";

    private final String KEY;

    public AesUtils(String key) {
        this.KEY = key;
    }

    /**
     * 生成密钥
     * @return 密钥
     */
    public static String generateKey() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
            keygen.init(128, new SecureRandom());
            SecretKey secretKey = keygen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private static SecretKeySpec getSecretKeySpec(String secretKeyStr) {
        return new SecretKeySpec(Base64.getDecoder().decode(secretKeyStr), ALGORITHM);
    }

    public String encrypt(String content) {
        try {
            return StringUtils.hasText(content) ? encrypt(content, this.KEY) : null;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     * @param content 待加密内容
     * @param secretKey 密钥
     * @return 加密结果
     */
    public static String encrypt(String content, String secretKey) {
        try {
            Key key = getSecretKeySpec(secretKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64.getEncoder().encodeToString(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8)));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     * @param content 加密内容
     * @return 解密结果
     */
    public String decrypt(String content) {
        try {
            return StringUtils.hasText(content) ? decrypt(content, this.KEY) : null;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     * @param content 加密内容
     * @param secretKey 密钥
     * @return 解密结果
     */
    public static String decrypt(String content, String secretKey) {
        try {
            Key key = getSecretKeySpec(secretKey);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(content)), StandardCharsets.UTF_8);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
