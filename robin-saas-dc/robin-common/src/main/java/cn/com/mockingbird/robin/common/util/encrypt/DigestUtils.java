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

    /**
     * 通过摘要算法对内容进行签名
     * @param content 要进行签名的内容
     * @param algorithm 算法 {@link Standard.Algorithm}
     * @return 签名结果
     */
    private static String digest(String content, String algorithm) {
        byte[] signature;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            signature = messageDigest.digest(content.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            log.error("An exception occurs during the signing of the content:", e);
            return Standard.Str.EMPTY;
        }
        return StringUtils.byteToHexString(signature);
    }

    /**
     * 完整性校验，即判断当前内容是否摘要算法处理过的原内容
     * @param content 要进行判断的内容
     * @param digest 摘要
     * @param algorithm 算法 {@link Standard.Algorithm}
     * @return true - 是
     */
    public static boolean isOriginalContent(String content, String digest, String algorithm) {
        return digest(content, algorithm).equals(digest);
    }

    public static String md5(String content) {
        return digest(content, Standard.Algorithm.MD5);
    }

    public static String sha1(String content) {
        return digest(content, Standard.Algorithm.SHA1);
    }

    public static String sha256(String content) {
        return digest(content, Standard.Algorithm.SHA256);
    }

    public static boolean isOriginalContentByMd5(String content, String digest) {
        return isOriginalContent(content, digest, Standard.Algorithm.MD5);
    }

    public static boolean isOriginalContentBySha1(String content, String digest) {
        return isOriginalContent(content, digest, Standard.Algorithm.SHA1);
    }

    public static boolean isOriginalContentBySha256(String content, String digest) {
        return isOriginalContent(content, digest, Standard.Algorithm.SHA256);
    }

}
