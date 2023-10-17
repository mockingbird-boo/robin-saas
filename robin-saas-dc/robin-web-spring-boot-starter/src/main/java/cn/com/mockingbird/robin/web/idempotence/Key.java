package cn.com.mockingbird.robin.web.idempotence;

/**
 * Key 工具类
 *
 * @author zhaopeng
 * @date 2023/10/18 1:11
 **/
public final class Key {

    public static String TOKEN_KEY_PATTERN = "idempotent:token:%s:%s";

    public static String generateTokenKey(String username, String token) {
        return String.format(TOKEN_KEY_PATTERN, username, token);
    }

}
