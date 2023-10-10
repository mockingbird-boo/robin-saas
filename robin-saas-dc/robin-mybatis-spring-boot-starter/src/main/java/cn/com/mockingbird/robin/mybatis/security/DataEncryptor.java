package cn.com.mockingbird.robin.mybatis.security;

/**
 * 数据加密器
 *
 * @author zhaopeng
 * @date 2023/10/10 22:55
 **/
public interface DataEncryptor {

    /**
     * 加密
     * @param content 原始数据
     * @return 加密数据
     */
    String encrypt(String content);


    /**
     * 解密
     * @param content 加密数据
     * @return 原始数据
     */
    String decrypt(String content);

}
