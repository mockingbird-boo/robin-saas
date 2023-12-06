package cn.com.mockingbird.robin.iam.support.security.password.exception;

import org.springframework.http.HttpStatus;

/**
 * 密码连续字符校验异常
 *
 * @author zhaopeng
 * @date 2023/12/6 19:53
 **/
@SuppressWarnings("serial")
public class PasswordContinuousSameCharInvalidException extends PasswordInvalidException {

    public PasswordContinuousSameCharInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordContinuousSameCharInvalidException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

    public PasswordContinuousSameCharInvalidException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public PasswordContinuousSameCharInvalidException(String error, String description, HttpStatus status) {
        super(error, description, status);
    }

    public PasswordContinuousSameCharInvalidException(Throwable cause, String error, String description, HttpStatus status) {
        super(cause, error, description, status);
    }

}
