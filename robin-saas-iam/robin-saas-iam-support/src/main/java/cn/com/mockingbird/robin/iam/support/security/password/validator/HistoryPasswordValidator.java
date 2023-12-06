package cn.com.mockingbird.robin.iam.support.security.password.validator;

import cn.com.mockingbird.robin.iam.support.security.password.PasswordValidator;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordHistoryInvalidException;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordInvalidException;
import com.google.common.collect.Lists;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * 历史密码校验器
 *
 * @author zhaopeng
 * @date 2023/12/6 19:52
 **/
public class HistoryPasswordValidator implements PasswordValidator {

    private final Boolean enabled;
    private final List<String> historyPasswords;
    private final PasswordEncoder passwordEncoder;

    public HistoryPasswordValidator(Boolean enabled) {
        this.enabled = enabled;
        this.historyPasswords = Lists.newArrayList();
        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public HistoryPasswordValidator(List<String> historyPasswords, PasswordEncoder passwordEncoder) {
        this.enabled = true;
        this.historyPasswords = historyPasswords;
        this.passwordEncoder = passwordEncoder;
    }

    public HistoryPasswordValidator(final Boolean enabled, final List<String> historyPasswords, final PasswordEncoder passwordEncoder) {
        this.enabled = enabled;
        this.historyPasswords = historyPasswords;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void validate(String password) throws PasswordInvalidException {
        if (this.enabled) {
            this.historyPasswords.forEach((i) -> {
                boolean matches = this.passwordEncoder.matches(password, i);
                if (matches) {
                    throw new PasswordHistoryInvalidException("当前密码与历史密码相同");
                }
            });
        }
    }
}
