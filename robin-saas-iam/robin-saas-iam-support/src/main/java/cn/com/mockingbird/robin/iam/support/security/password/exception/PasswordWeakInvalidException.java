package cn.com.mockingbird.robin.iam.support.security.password.exception;

import cn.com.mockingbird.robin.iam.support.exception.IamException;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 弱密码验证异常
 *
 * @author zhaopeng
 * @date 2023/12/6 18:43
 **/
public class PasswordWeakInvalidException extends PasswordInvalidException {

    @Serial
    private static final long serialVersionUID = 6513486848272304115L;

    public PasswordWeakInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordWeakInvalidException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

    public PasswordWeakInvalidException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public PasswordWeakInvalidException(String error, String description, HttpStatus status) {
        super(error, description, status);
    }

    public PasswordWeakInvalidException(Throwable cause, String error, String description, HttpStatus status) {
        super(cause, error, description, status);
    }

}
