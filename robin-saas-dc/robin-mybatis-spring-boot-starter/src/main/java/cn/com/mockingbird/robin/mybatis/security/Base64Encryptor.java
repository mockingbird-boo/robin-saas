package cn.com.mockingbird.robin.mybatis.security;

import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base 64 加密解密器
 *
 * @author zhaopeng
 * @date 2023/10/11 1:22
 **/
@Slf4j
public final class Base64Encryptor implements DataEncryptor {
    @Override
    public String encrypt(String content) {
        try {
            return Base64.getEncoder().encodeToString(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Failed to encrypt content!", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String decrypt(String content) {
        try {
            byte[] asBytes = Base64.getDecoder().decode(content);
            return new String(asBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Failed to decrypt content!", e);
            throw new RuntimeException(e.getMessage());
        }
    }
}
