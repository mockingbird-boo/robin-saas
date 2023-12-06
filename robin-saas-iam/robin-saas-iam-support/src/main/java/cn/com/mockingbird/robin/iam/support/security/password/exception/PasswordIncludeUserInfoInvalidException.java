package cn.com.mockingbird.robin.iam.support.security.password.exception;

import org.springframework.http.HttpStatus;

/**
 * 密码涉及用户信息校验异常
 *
 * @author zhaopeng
 * @date 2023/12/6 20:01
 **/
@SuppressWarnings("serial")
public class PasswordIncludeUserInfoInvalidException extends PasswordInvalidException {

    public PasswordIncludeUserInfoInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordIncludeUserInfoInvalidException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

    public PasswordIncludeUserInfoInvalidException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public PasswordIncludeUserInfoInvalidException(String error, String description, HttpStatus status) {
        super(error, description, status);
    }

    public PasswordIncludeUserInfoInvalidException(Throwable cause, String error, String description, HttpStatus status) {
        super(cause, error, description, status);
    }

}
