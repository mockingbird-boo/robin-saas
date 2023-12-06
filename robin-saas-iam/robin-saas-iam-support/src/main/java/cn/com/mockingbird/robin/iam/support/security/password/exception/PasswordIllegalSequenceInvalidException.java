package cn.com.mockingbird.robin.iam.support.security.password.exception;

import org.springframework.http.HttpStatus;

/**
 * 密码非法序列化校验异常
 *
 * @author zhaopeng
 * @date 2023/12/6 20:00
 **/
@SuppressWarnings("serial")
public class PasswordIllegalSequenceInvalidException extends PasswordInvalidException {

    public PasswordIllegalSequenceInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordIllegalSequenceInvalidException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

    public PasswordIllegalSequenceInvalidException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public PasswordIllegalSequenceInvalidException(String error, String description, HttpStatus status) {
        super(error, description, status);
    }

    public PasswordIllegalSequenceInvalidException(Throwable cause, String error, String description, HttpStatus status) {
        super(cause, error, description, status);
    }

}
