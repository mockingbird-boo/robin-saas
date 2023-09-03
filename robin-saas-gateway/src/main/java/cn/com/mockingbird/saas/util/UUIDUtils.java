package cn.com.mockingbird.saas.util;

import java.util.UUID;

/**
 * UUID 生成工具类
 *
 * @author zhaopeng
 * @date 2023/8/23 1:37
 **/
public class UUIDUtils {

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String generateShortUUID() {
        // JDK提供的UUID：32位、含“-”
        String uuid = UUID.randomUUID().toString().replace("-", "");
        StringBuffer shortUuid = new StringBuffer();
        // 分成8组
        for (int i = 0; i < 8; i++) {
            // 每组4位刚好是一个16进制数
            String str = uuid.substring(i * 4, i * 4 + 4);
            // 得到该16进制数对应的十进制数
            int num = Integer.parseInt(str, 16);
            // 取模62（16进制表示为314，即0x3E），结果作为索引取出字符
            shortUuid.append(chars[num % 0x3E]);
        }
        return shortUuid.toString();
    }

    public static void main(String[] args) {
        String s = generateShortUUID();
        System.out.println(s);
    }

}
