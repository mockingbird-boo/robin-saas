package cn.com.mockingbird.robin.iam.support.security.password.exception;

import cn.com.mockingbird.robin.iam.support.exception.IamException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 密码长度验证异常
 *
 * @author zhaopeng
 * @date 2023/12/6 18:42
 **/
public class PasswordLengthInvalidException extends PasswordInvalidException {

    @Serial
    private static final long serialVersionUID = 7293903056805953740L;

    public PasswordLengthInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordLengthInvalidException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

    public PasswordLengthInvalidException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public PasswordLengthInvalidException(String error, String description, HttpStatus status) {
        super(error, description, status);
    }

    public PasswordLengthInvalidException(Throwable cause, String error, String description, HttpStatus status) {
        super(cause, error, description, status);
    }

}
