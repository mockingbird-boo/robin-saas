package cn.com.mockingbird.robin.iam.support.security.password.validator;

import cn.com.mockingbird.robin.iam.support.security.password.PasswordValidator;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordInvalidException;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordWeakInvalidException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 弱密码校验器
 *
 * @author zhaopeng
 * @date 2023/12/6 19:33
 **/
public class WeakPasswordValidator implements PasswordValidator {

    private final Boolean enabled;

    private final Set<String> dictionary;

    public WeakPasswordValidator(Boolean enabled) {
        this.enabled = enabled;
        this.dictionary = new HashSet<>(16);
    }

    public WeakPasswordValidator(List<String> list) {
        this.enabled = true;
        this.dictionary = new HashSet<>(list);
    }

    public WeakPasswordValidator(final Boolean enabled, final Set<String> dictionary) {
        this.enabled = enabled;
        this.dictionary = dictionary;
    }

    @Override
    public void validate(String password) throws PasswordInvalidException {
        if (this.enabled && this.dictionary.contains(password)) {
            throw new PasswordWeakInvalidException("密码为弱密码");
        }
    }

}
