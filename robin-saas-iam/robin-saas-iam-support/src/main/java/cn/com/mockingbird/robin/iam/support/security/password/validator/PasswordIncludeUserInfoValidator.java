package cn.com.mockingbird.robin.iam.support.security.password.validator;

import cn.com.mockingbird.robin.iam.support.security.password.PasswordValidator;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordIncludeUserInfoInvalidException;
import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordInvalidException;
import cn.com.mockingbird.robin.iam.support.util.Pinyin4jUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.passay.MatchBehavior;
import org.passay.PasswordData;
import org.passay.RuleResult;
import org.passay.UsernameRule;

/**
 * 密码包含用户相关信息校验器
 *
 * @author zhaopeng
 * @date 2023/12/6 19:57
 **/
@Slf4j
@Getter
public class PasswordIncludeUserInfoValidator implements PasswordValidator {

    MatchBehavior matchBehavior;
    private final Boolean enabled;
    private final String realName;
    private final String nickName;
    private final String username;
    private final String phone;
    private final String email;

    public PasswordIncludeUserInfoValidator(Boolean enabled) {
        this.matchBehavior = MatchBehavior.Contains;
        this.enabled = enabled;
        this.realName = "";
        this.nickName = "";
        this.username = "";
        this.phone = "";
        this.email = "";
    }

    public PasswordIncludeUserInfoValidator(String realName, String nickName, String username, String phone, String email) {
        this.matchBehavior = MatchBehavior.Contains;
        this.enabled = true;
        this.realName = realName;
        this.nickName = nickName;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    public PasswordIncludeUserInfoValidator(final Boolean enabled, final String realName, final String nickName, final String username, final String phone, final String email) {
        this.matchBehavior = MatchBehavior.Contains;
        this.enabled = enabled;
        this.realName = realName;
        this.nickName = nickName;
        this.username = username;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public void validate(String password) throws PasswordInvalidException {
        if (this.enabled) {
            if (StringUtils.isNoneBlank(this.username)) {
                UsernameRule usernameRule = new UsernameRule();
                org.passay.PasswordValidator validator = new org.passay.PasswordValidator(usernameRule);
                PasswordData passwordData = new PasswordData(password);
                passwordData.setUsername(this.username);
                RuleResult result = validator.validate(passwordData);
                if (!result.isValid()) {
                    log.error("密码包含用户名信息");
                    throw new PasswordIncludeUserInfoInvalidException("密码包含用户名信息");
                }
            }

            if (StringUtils.isNoneBlank(this.phone) && this.matchBehavior.match(password, this.phone)) {
                log.error("密码包含手机号信息");
                throw new PasswordIncludeUserInfoInvalidException("密码包含手机号信息");
            }

            if (StringUtils.isNoneBlank(this.realName) && (this.matchBehavior.match(password, Pinyin4jUtils.getFirstSpellPinYin(this.realName, true)) || this.matchBehavior.match(password, Pinyin4jUtils.getFirstSpellPinYin(this.realName, false)) || this.matchBehavior.match(password, Pinyin4jUtils.getPinYinHeadChar(this.realName)))) {
                log.error("密码包含姓名拼音信息");
                throw new PasswordIncludeUserInfoInvalidException("密码包含姓名拼音信息");
            }

            if (StringUtils.isNoneBlank(this.nickName) && (this.matchBehavior.match(password, this.nickName) || this.matchBehavior.match(password, Pinyin4jUtils.getFirstSpellPinYin(this.nickName, true)) || this.matchBehavior.match(password, Pinyin4jUtils.getFirstSpellPinYin(this.nickName, false)) || this.matchBehavior.match(password, Pinyin4jUtils.getPinYinHeadChar(this.nickName)))) {
                log.error("密码包含昵称信息");
                throw new PasswordIncludeUserInfoInvalidException("密码包含昵称信息");
            }

            if (StringUtils.isNoneBlank(this.email)) {
                int splitPosition = this.email.lastIndexOf(64);
                if (splitPosition > 0) {
                    String localPart = this.email.substring(0, splitPosition);
                    if (this.matchBehavior.match(password, localPart)) {
                        log.error("密码包含邮箱前缀信息");
                        throw new PasswordIncludeUserInfoInvalidException("密码包含邮箱前缀信息");
                    }
                }
            }
        }
    }
}
