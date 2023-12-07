package cn.com.mockingbird.robin.iam.common.enums;

import lombok.Getter;

/**
 * 用户性别枚举
 *
 * @author zhaopeng
 * @date 2023/12/6 23:54
 **/
@Getter
public enum UserGender {

    MALE(1, "男"),
    FEMALE(2, "女"),
    UNKNOWN(3, "未知");

    private final Integer code;

    private final String description;

    UserGender(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 根据 code 获取 UserGender
     * @param code code
     * @return UserGender 枚举
     */
    public static UserGender getType(Integer code) {
        UserGender[] values = values();
        for (UserGender status : values) {
            if (status.getCode() == null ? code == null : status.getCode().equals(code)) {
                return status;
            }
        }
        throw new NullPointerException("性别不存在");
    }

}
