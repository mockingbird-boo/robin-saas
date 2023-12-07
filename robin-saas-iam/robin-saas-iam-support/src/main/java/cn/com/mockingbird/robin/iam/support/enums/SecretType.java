package cn.com.mockingbird.robin.iam.support.enums;

import lombok.Getter;

/**
 * 密钥枚举
 *
 * @author zhaopeng
 * @date 2023/12/7 3:26
 **/
public enum SecretType implements BaseEnum {

    LOGIN("login", "IAM_LOGIN_SECRET", "登录"),
    ENCRYPT("encrypt", "IAM_ENCRYPT_SECRET", "加密");

    private final String code;
    @Getter
    private final String key;
    private final String desc;

    SecretType(String code, String key, String desc) {
        this.code = code;
        this.key = key;
        this.desc = desc;
    }

    public static SecretType getType(String code) {
        SecretType[] values = values();
        for (SecretType source : values) {
            if (String.valueOf(source.getCode()).equals(code)) {
                return source;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.desc;
    }

}
