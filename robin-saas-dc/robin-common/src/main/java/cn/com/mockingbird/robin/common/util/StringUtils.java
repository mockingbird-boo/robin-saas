package cn.com.mockingbird.robin.common.util;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 字符串工具类
 * <p>
 * 更多的字符串操作的可以使用 {@link org.apache.commons.lang3.StringUtils} 等框架提供的字符串工具类
 * 本工具类主要用来提供一些特殊的字符串操作方法，用于支持当前项目中开发的一些组件
 *
 * @author zhaopeng
 * @date 2023/10/9 23:15
 **/
public final class StringUtils {

    public static final String COMMA = ",";

    /**
     * 首字符大写
     * @param str 目标字符串
     * @return 首字符大写后的字符串
     */
    public static String upperTheFirstChar(String str) {
        // 进行字母的 ascii 编码前移，效率略高于截取字符串进行转换的操作
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 逗号分隔的字符串转 List<String>
     * @param str 逗号分隔的字符
     * @return List<String> 实例
     */
    public static List<String> commaStringToList(String str) {
        if (str == null) {
            return null;
        }
        return Arrays.asList(str.trim().split(COMMA));
    }

    public static String listToCommaString(List<String> strList) {
        if (strList == null) {
            return null;
        }
        if (strList.isEmpty()) {
            return "";
        }
        return String.join(COMMA, strList);
    }

}
