package cn.com.mockingbird.robin.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串驼峰转换工具类
 *
 * @author zhaopeng
 * @date 2023/10/9 1:09
 **/
public final class StringCamelUtils {

    private static final char UNDERLINE = '_';

    /**
     * 驼峰转下划线
     * @param camelStr 驼峰字符串
     * @return 驼峰转下划线的结果字符串
     */
    public static String humpToUnderline(String camelStr) {
        if (StringUtils.isBlank(camelStr)) {
            return camelStr;
        }
        int length = camelStr.length();
        StringBuilder sb = new StringBuilder(length + 2);
        char pre = 0;
        for (int i = 0; i < length; i++) {
            char c = camelStr.charAt(i);
            if (Character.isUpperCase(c)) {
                if (pre != UNDERLINE && i != 0) {
                    sb.append(UNDERLINE);
                }
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
            pre = c;
        }

        return sb.toString();
    }

    /**
     * 下划线字符串转驼峰
     * @param underlineStr 下划线字符串
     * @return 下划线字符串转驼峰的结果字符串
     */
    public static String underlineToCamel(String underlineStr) {
        if (StringUtils.isBlank(underlineStr)) {
            return underlineStr;
        }
        int length = underlineStr.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char c = underlineStr.charAt(i);
            if (c == UNDERLINE) {
                if (++i < length) {
                    sb.append(Character.toUpperCase(underlineStr.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
