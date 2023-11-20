package cn.com.mockingbird.robin.web.desensitize;

import lombok.Getter;

import java.util.function.Function;

/**
 * 脱敏类型
 *
 * @author zhaopeng
 * @date 2023/11/21 0:31
 **/
@Getter
public enum DesensitizeType {

    /**
     * 中文名
     */
    NAME(s -> s.replaceAll("(\\S)\\S(\\S*)", "$1*$2")),

    /**
     * 身份证
     */
    ID_CARD(s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2")),

    /**
     * 座机号
     */
    FIXED_PHONE(s -> s.replaceAll("\\S+(\\d{4})", "****$1")),

    /**
     * 手机号
     */
    MOBILE_PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),

    /**
     * 地址
     */
    ADDRESS(s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****")),

    /**
     * 电子邮件
     */
    EMAIL(s -> s.replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4")),

    /**
     * 银行卡
     */
    BANK_CARD(s -> s.replaceAll("(\\d{6})\\d{9}(\\d{4})", "$1****$2")),

    /**
     * 秘钥
     */
    SECRET(s -> s.replaceAll("(\\w{3})\\w*(\\w{3})", "$1****$2")),

    CUSTOMIZED(s -> s);

    private final Function<String, String> desensitizer;

    DesensitizeType(Function<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    /**
     * 对字符串进行脱敏操作
     * @param origin 原始字符串
     * @param prefix 左侧需要保留几位明文字段
     * @param suffix 右侧需要保留几位明文字段
     * @param mark 用于遮罩的字符串, 如 '*'
     * @return 脱敏后结果
     */
    public static String desensitize(String origin, int prefix, int suffix, String mark) {
        if (origin == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = origin.length(); i < n; i++) {
            if (i < prefix) {
                sb.append(origin.charAt(i));
                continue;
            }
            if (i > (n - suffix - 1)) {
                sb.append(origin.charAt(i));
                continue;
            }
            sb.append(mark);
        }
        return sb.toString();
    }

}
