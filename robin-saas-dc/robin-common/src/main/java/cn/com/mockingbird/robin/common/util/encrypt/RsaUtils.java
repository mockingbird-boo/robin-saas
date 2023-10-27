package cn.com.mockingbird.robin.common.util.encrypt;

import cn.com.mockingbird.robin.common.constant.Standard;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 加密工具
 *
 * @author zhaopeng
 * @date 2023/10/26 0:58
 **/
@SuppressWarnings("unused")
@UtilityClass
public class RsaUtils {

    private final int KEY_SIZE = 1024;
    /**
     * 分段加密的大小
     */
    private final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * 分段解密的大小
     */
    private final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 生成密钥对并写入文件
     * @param priKeyPath 私钥路径
     * @param pubKeyPath 公钥路径
     * @throws IOException 文件IO异常
     */
    public void generateFile(String priKeyPath, String pubKeyPath) throws IOException {
        KeyPair keyPair = getKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        String base64PrivateKey = Base64Utils.encode(privateKey.getEncoded());
        String base64PublicKey = Base64Utils.encode(publicKey.getEncoded());
        FileUtils.writeStringToFile(new File(priKeyPath), base64PrivateKey, StandardCharsets.UTF_8);
        FileUtils.writeStringToFile(new File(pubKeyPath), base64PublicKey, StandardCharsets.UTF_8);
    }

    /**
     * 生成密钥对
     * @return 密钥对
     */
    public KeyPair getKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(Standard.Algorithm.RSA);
            // KEY 的大小
            generator.initialize(KEY_SIZE);
            return generator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPrivateKey(PrivateKey privateKey) {
        return Base64Utils.encode(privateKey.getEncoded());
    }

    public String getPublicKey(PublicKey publicKey) {
        return Base64Utils.encode(publicKey.getEncoded());
    }

    public PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode2Bytes(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Standard.Algorithm.RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    public PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode2Bytes(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Standard.Algorithm.RSA);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 公钥加密
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return Base64 编码后的加密字符串
     */
    public String encrypt(String data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(Standard.Algorithm.RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            int dataLength = data.getBytes().length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            while (dataLength - offset > 0) {
                if (dataLength - offset > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data.getBytes(), offset, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data.getBytes(), offset, dataLength - offset);
                }
                out.write(cache, 0, cache.length);
                i++;
                offset = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            return Base64Utils.encode(encryptedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String data, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(Standard.Algorithm.RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] dataBytes = Base64Utils.decode2Bytes(data);
            int dataLength = dataBytes.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offset = 0;
            byte[] cache;
            int i = 0;
            while (dataLength - offset > 0) {
                if (dataLength - offset > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(dataBytes, offset, dataLength - offset);
                }
                out.write(cache, 0, cache.length);
                i ++;
                offset = i * MAX_DECRYPT_BLOCK;
            }
            out.close();
            return out.toString(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 数据签名
     * @param data 原始数据
     * @param privateKey 私钥
     * @return 签名数据
     */
    public String sign(String data, PrivateKey privateKey) {
        try {
            byte[] keyBytes = privateKey.getEncoded();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(Standard.Algorithm.RSA);
            PrivateKey key = keyFactory.generatePrivate(keySpec);
            Signature signature = Signature.getInstance(Standard.Algorithm.MD5_WITH_RSA);
            signature.initSign(key);
            signature.update(data.getBytes());
            return Base64Utils.encode(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验签
     * @param data 原始数据
     * @param publicKey 公钥
     * @param signData 签名数据
     * @return true - 验证通过
     */
    public boolean isOriginalSign(String data, PublicKey publicKey, String signData) {
        try {
            byte[] keyBytes = publicKey.getEncoded();
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(Standard.Algorithm.RSA);
            PublicKey key = keyFactory.generatePublic(keySpec);
            Signature signature = Signature.getInstance(Standard.Algorithm.MD5_WITH_RSA);
            signature.initVerify(key);
            signature.update(data.getBytes());
            return signature.verify(Base64Utils.decode2Bytes(signData));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
