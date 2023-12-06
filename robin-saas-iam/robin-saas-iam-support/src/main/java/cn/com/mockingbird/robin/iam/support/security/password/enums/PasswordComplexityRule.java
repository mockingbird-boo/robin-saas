package cn.com.mockingbird.robin.iam.support.security.password.enums;

import lombok.Getter;

/**
 * 密码复杂性规则枚举
 *
 * @author zhaopeng
 * @date 2023/12/6 19:37
 **/
@Getter
public enum PasswordComplexityRule {

    NONE("00", "任意密码"),
    MUST_NUMBERS_AND_LETTERS("01", "必须包含数字和字母"),
    MUST_NUMBERS_AND_CAPITAL_LETTERS("02", "必须包含数字和大写字母"),
    MUST_CONTAIN_NUMBERS_UPPERCASE_LETTERS_LOWERCASE_LETTERS_AND_SPECIAL_CHARACTERS("03", "必须包含数字、大写字母、小写字母、和特殊字符"),
    CONTAIN_AT_LEAST_TWO_OF_NUMBERS_LETTERS_AND_SPECIAL_CHARACTERS("04", "至少包含数字、字母、和特殊字符中的两种"),
    CONTAIN_AT_LEAST_THREE_OF_NUMBERS_UPPERCASE_LETTERS_LOWERCASE_LETTERS_AND_SPECIAL_CHARACTERS("05", "至少包含数字、大写字母、小写字母、和特殊字符中的三种");

    private final String code;
    private final String desc;

    PasswordComplexityRule(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PasswordComplexityRule getType(String code) {
        PasswordComplexityRule[] values = values();
        for (PasswordComplexityRule status : values) {
            if (String.valueOf(status.getCode()).equals(code)) {
                return status;
            }
        }
        throw new NullPointerException("规则不存在");
    }

    @Override
    public String toString() {
        return this.code;
    }

}
