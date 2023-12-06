package cn.com.mockingbird.robin.iam.support.security.password;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 无密码编码器
 *
 * @author zhaopeng
 * @date 2023/12/6 20:10
 **/
public class NoopPasswordEncoder implements PasswordEncoder {

    private static final PasswordEncoder INSTANCE = new NoopPasswordEncoder();

    private NoopPasswordEncoder() {

    }

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return StringUtils.equals(rawPassword, encodedPassword);
    }

    public static PasswordEncoder getInstance() {
        return INSTANCE;
    }

}
