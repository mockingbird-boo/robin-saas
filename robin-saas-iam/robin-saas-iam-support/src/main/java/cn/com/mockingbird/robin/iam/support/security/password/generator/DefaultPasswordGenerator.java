package cn.com.mockingbird.robin.iam.support.security.password.generator;

import cn.com.mockingbird.robin.iam.support.security.password.PasswordGenerator;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;

import java.util.Arrays;
import java.util.List;

/**
 * 默认的密码生成器
 *
 * @author zhaopeng
 * @date 2023/12/6 18:57
 **/
public class DefaultPasswordGenerator implements PasswordGenerator {

    private final List<CharacterRule> rules;

    public DefaultPasswordGenerator() {
        this.rules = Arrays.asList(new CharacterRule(EnglishCharacterData.UpperCase, 2),
                new CharacterRule(EnglishCharacterData.LowerCase, 2),
                new CharacterRule(EnglishCharacterData.Digit, 2));
    }

    @Override
    public String generatePassword() {
        org.passay.PasswordGenerator generator = new org.passay.PasswordGenerator();
        return generator.generatePassword(10, this.rules);
    }

}
