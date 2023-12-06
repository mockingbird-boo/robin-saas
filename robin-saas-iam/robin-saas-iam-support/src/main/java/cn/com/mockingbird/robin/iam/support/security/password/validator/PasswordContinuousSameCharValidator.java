package cn.com.mockingbird.robin.iam.support.security.password.validator;

import cn.com.mockingbird.robin.iam.support.security.password.PasswordValidator;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordContinuousSameCharInvalidException;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordInvalidException;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.extern.slf4j.Slf4j;
import org.passay.PasswordData;
import org.passay.RepeatCharactersRule;
import org.passay.Rule;
import org.passay.RuleResult;

/**
 * 密码连续字符校验器
 *
 * @author zhaopeng
 * @date 2023/12/6 19:55
 **/
@Slf4j
public class PasswordContinuousSameCharValidator implements PasswordValidator {

    private final Integer rule;

    public PasswordContinuousSameCharValidator(final Integer rule) {
        this.rule = rule;
    }

    @Override
    public void validate(String password) throws PasswordInvalidException {
        RepeatCharactersRule repeatCharactersRule = new RepeatCharactersRule(this.rule);
        org.passay.PasswordValidator passwordValidator = new org.passay.PasswordValidator(repeatCharactersRule);
        RuleResult validate = passwordValidator.validate(new PasswordData(password));
        if (!validate.isValid()) {
            log.error("密码存在连续相同字符数: {}", JSONObject.toJSONString(validate.getDetails()));
            throw new PasswordContinuousSameCharInvalidException("密码存在连续相同字符数");
        }
    }

}
