package cn.com.mockingbird.robin.iam.common.enums;

import lombok.Getter;

/**
 * 用户状态枚举
 *
 * @author zhaopeng
 * @date 2023/12/7 0:00
 **/
@Getter
public enum UserStatus {

    /**
     * 启用
     */
    ENABLE(1, "enabled"),

    /**
     * 禁用
     */
    DISABLE(2, "disabled"),

    /**
     * 锁定
     */
    LOCKED(3, "locked"),

    /**
     * 过期锁定
     */
    EXPIRED_LOCKED(4, "expired_locked"),

    /**
     * 密码过期锁定
     */
    PASSWORD_EXPIRED_LOCKED(5, "password_expired_locked");
    ;

    private final Integer code;

    private final String description;

    UserStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public UserStatus getUserStatus(Integer code) {
        UserStatus[] values = values();
        for (UserStatus status : values) {
            if (status.code == null ? code == null : status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return description;
    }
}
