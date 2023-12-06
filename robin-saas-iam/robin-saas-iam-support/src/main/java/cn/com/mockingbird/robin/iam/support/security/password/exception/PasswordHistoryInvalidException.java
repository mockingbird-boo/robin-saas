package cn.com.mockingbird.robin.iam.support.security.password.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 密码历史验证异常
 *
 * @author zhaopeng
 * @date 2023/12/6 18:46
 **/
public class PasswordHistoryInvalidException extends PasswordInvalidException {

    @Serial
    private static final long serialVersionUID = -4757512869897154350L;

    public PasswordHistoryInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordHistoryInvalidException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

    public PasswordHistoryInvalidException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public PasswordHistoryInvalidException(String error, String description, HttpStatus status) {
        super(error, description, status);
    }

    public PasswordHistoryInvalidException(Throwable cause, String error, String description, HttpStatus status) {
        super(cause, error, description, status);
    }

}
