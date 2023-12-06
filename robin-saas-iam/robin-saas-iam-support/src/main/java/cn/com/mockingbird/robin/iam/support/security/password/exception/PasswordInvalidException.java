package cn.com.mockingbird.robin.iam.support.security.password.exception;

import cn.com.mockingbird.robin.iam.support.exception.IamException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 密码验证验证异常
 *
 * @author zhaopeng
 * @date 2023/12/6 18:40
 **/
public class PasswordInvalidException extends IamException {

    @Serial
    private static final long serialVersionUID = 6975137865316782024L;

    public PasswordInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordInvalidException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

    public PasswordInvalidException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public PasswordInvalidException(String error, String description, HttpStatus status) {
        super(error, description, status);
    }

    public PasswordInvalidException(Throwable cause, String error, String description, HttpStatus status) {
        super(cause, error, description, status);
    }

}
