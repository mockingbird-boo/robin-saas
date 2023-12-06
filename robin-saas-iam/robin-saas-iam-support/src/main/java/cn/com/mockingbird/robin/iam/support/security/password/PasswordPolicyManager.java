package cn.com.mockingbird.robin.iam.support.security.password;

import cn.com.mockingbird.robin.iam.support.security.password.exception.PasswordInvalidException;
import org.springframework.lang.NonNull;

/**
 * 密码策略管理器接口
 *
 * @author zhaopeng
 * @date 2023/12/6 18:49
 **/
public interface PasswordPolicyManager<T> {

    /**
     * 密码验证
     * @param user 用户
     * @param password 密码
     * @throws PasswordInvalidException 密码验证异常
     */
    void validate(@NonNull T user, String password) throws PasswordInvalidException;

}
