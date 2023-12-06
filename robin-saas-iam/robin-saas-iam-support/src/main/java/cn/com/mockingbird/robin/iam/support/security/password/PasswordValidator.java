package cn.com.mockingbird.robin.iam.support.security.password;

import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordInvalidException;

/**
 * 密码校验器接口
 *
 * @author zhaopeng
 * @date 2023/12/6 18:22
 **/
public interface PasswordValidator {

    /**
     * 验证密码
     * @param password 密码字符串
     * @throws PasswordInvalidException 密码验证异常
     */
    void validate(String password) throws PasswordInvalidException;

}
