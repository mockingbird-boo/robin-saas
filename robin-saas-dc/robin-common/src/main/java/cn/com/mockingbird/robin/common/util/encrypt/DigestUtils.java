package cn.com.mockingbird.robin.common.util.encrypt;

import cn.com.mockingbird.robin.common.constant.Standard;
import cn.com.mockingbird.robin.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要算法工具类
 *
 * @author zhaopeng
 * @date 2023/10/20 21:45
 **/
@Slf4j
@SuppressWarnings("unused")
public final class DigestUtils {

    private static String digest(String message, String algorithm) {
        byte[] signature;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            signature = messageDigest.digest(message.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            log.error("An exception occurs during the signing of the message:", e);
            return Standard.Str.EMPTY;
        }
        return StringUtils.byteToHexString(signature);
    }

    public static String md5(String message) {
        return digest(message, Standard.Algorithm.MD5);
    }

    public static String sha1(String message) {
        return digest(message, Standard.Algorithm.SHA1);
    }

    public static String sha256(String message) {
        return digest(message, Standard.Algorithm.SHA256);
    }

}
