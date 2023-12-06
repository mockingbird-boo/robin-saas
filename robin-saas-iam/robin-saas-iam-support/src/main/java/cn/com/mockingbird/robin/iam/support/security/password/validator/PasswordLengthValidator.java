package cn.com.mockingbird.robin.iam.support.security.password.validator;

import cn.com.mockingbird.robin.iam.support.security.password.PasswordValidator;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordInvalidException;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordLengthInvalidException;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.RuleResult;

/**
 * 密码长度校验器
 *
 * @author zhaopeng
 * @date 2023/12/6 19:35
 **/
@Slf4j
public class PasswordLengthValidator implements PasswordValidator {

    private final Integer minLength;

    private final Integer maxLength;

    public PasswordLengthValidator(final Integer minLength, final Integer maxLength) {
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public void validate(String password) throws PasswordInvalidException {
        LengthRule lengthRule = new LengthRule(this.minLength, this.maxLength);
        RuleResult validate = lengthRule.validate(new PasswordData(password));
        if (!validate.isValid()) {
            log.error("密码不符合长度规则: [{}]", JSONObject.toJSONString(validate.getDetails()));
            throw new PasswordLengthInvalidException("密码不符合长度规则");
        }
    }
}
