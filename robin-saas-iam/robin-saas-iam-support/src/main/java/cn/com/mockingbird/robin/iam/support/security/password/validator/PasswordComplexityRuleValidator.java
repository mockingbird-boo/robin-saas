package cn.com.mockingbird.robin.iam.support.security.password.validator;

import cn.com.mockingbird.robin.iam.support.security.password.PasswordValidator;
import cn.com.mockingbird.robin.iam.support.security.password.enums.PasswordComplexityRule;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordComplexityRuleInvalidException;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordInvalidException;
import org.passay.*;

/**
 * 密码复杂性规则校验器
 *
 * @author zhaopeng
 * @date 2023/12/6 19:48
 **/
public class PasswordComplexityRuleValidator implements PasswordValidator {

    private final PasswordComplexityRule rule;

    public PasswordComplexityRuleValidator(final PasswordComplexityRule rule) {
        this.rule = rule;
    }

    @Override
    public void validate(String password) throws PasswordInvalidException {
        if (!this.rule.equals(PasswordComplexityRule.NONE)) {
            org.passay.PasswordValidator validator;
            RuleResult validate;
            if (this.rule.equals(PasswordComplexityRule.MUST_NUMBERS_AND_LETTERS)) {
                validator = new org.passay.PasswordValidator(new CharacterRule(EnglishCharacterData.Digit, 1), new CharacterRule(EnglishCharacterData.Alphabetical, 1));
                validate = validator.validate(new PasswordData(password));
                if (!validate.isValid()) {
                    throw new PasswordComplexityRuleInvalidException("密码必须包含数字和字母");
                }
            } else if (this.rule.equals(PasswordComplexityRule.MUST_NUMBERS_AND_CAPITAL_LETTERS)) {
                validator = new org.passay.PasswordValidator(new CharacterRule(EnglishCharacterData.Digit, 1), new CharacterRule(EnglishCharacterData.UpperCase, 1));
                validate = validator.validate(new PasswordData(password));
                if (!validate.isValid()) {
                    throw new PasswordComplexityRuleInvalidException("密码必须包含数字和大写字母");
                }
            } else if (this.rule.equals(PasswordComplexityRule.MUST_CONTAIN_NUMBERS_UPPERCASE_LETTERS_LOWERCASE_LETTERS_AND_SPECIAL_CHARACTERS)) {
                validator = new org.passay.PasswordValidator(new Rule[]{new CharacterRule(EnglishCharacterData.Alphabetical, 1), new CharacterRule(EnglishCharacterData.Digit, 1), new CharacterRule(EnglishCharacterData.Special, 1)});
                validate = validator.validate(new PasswordData(password));
                if (!validate.isValid()) {
                    throw new PasswordComplexityRuleInvalidException("密码必须包含数字、大写字母、小写字母、和特殊字符");
                }
            } else {
                CharacterCharacteristicsRule rule;
                if (this.rule.equals(PasswordComplexityRule.CONTAIN_AT_LEAST_TWO_OF_NUMBERS_LETTERS_AND_SPECIAL_CHARACTERS)) {
                    rule = new CharacterCharacteristicsRule(new CharacterRule(EnglishCharacterData.Digit, 1), new CharacterRule(EnglishCharacterData.Special, 1), new CharacterRule(EnglishCharacterData.Alphabetical, 1));
                    rule.setNumberOfCharacteristics(2);
                    validator = new org.passay.PasswordValidator(rule);
                    validate = validator.validate(new PasswordData(password));
                    if (!validate.isValid()) {
                        throw new PasswordComplexityRuleInvalidException("密码至少包含数字、字母、和特殊字符中的两种");
                    }
                } else if (this.rule.equals(PasswordComplexityRule.CONTAIN_AT_LEAST_THREE_OF_NUMBERS_UPPERCASE_LETTERS_LOWERCASE_LETTERS_AND_SPECIAL_CHARACTERS)) {
                    rule = new CharacterCharacteristicsRule(new CharacterRule(EnglishCharacterData.Digit, 1), new CharacterRule(EnglishCharacterData.Special, 1), new CharacterRule(EnglishCharacterData.LowerCase, 1), new CharacterRule(EnglishCharacterData.UpperCase, 1));
                    rule.setNumberOfCharacteristics(3);
                    validator = new org.passay.PasswordValidator(rule);
                    validate = validator.validate(new PasswordData(password));
                    if (!validate.isValid()) {
                        throw new PasswordComplexityRuleInvalidException("密码至少包含数字、字母、和特殊字符中的两种");
                    }
                } else {
                    throw new PasswordComplexityRuleInvalidException("密码密码复杂规则不通过");
                }
            }
        }
    }
}
