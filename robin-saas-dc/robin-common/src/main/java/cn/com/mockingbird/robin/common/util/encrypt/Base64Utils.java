package cn.com.mockingbird.robin.common.util.encrypt;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64 工具类
 *
 * @author zhaopeng
 * @date 2023/10/26 2:17
 **/
@SuppressWarnings("unused")
@UtilityClass
public class Base64Utils {

    /**
     * 编码
     * @param content 待编码内容
     * @return 编码结果
     */
    public String encode(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    /**
     * 编码
     * @param content 待编码内容
     * @return 编码结果
     */
    public String encode(String content) {
        return encode(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 解码
     *
     * @param content 编码内容
     * @return 解码结果
     */
    public String decode(byte[] content) {
        return new String(Base64.getDecoder().decode(content), StandardCharsets.UTF_8);
    }

    /**
     * 解码
     * @param content 编码内容
     * @return 解码结果
     */
    public String decode(String content) {
        return decode(content.getBytes(StandardCharsets.ISO_8859_1));
    }

    /**
     * 解码
     * @param content 编码内容
     * @return 字节数组
     */
    public byte[] decode2Bytes(String content) {
        return Base64.getDecoder().decode(content);
    }

}
