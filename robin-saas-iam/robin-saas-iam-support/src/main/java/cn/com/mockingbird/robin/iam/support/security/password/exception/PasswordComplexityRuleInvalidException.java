package cn.com.mockingbird.robin.iam.support.security.password.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 密码复杂性规则校验异常
 *
 * @author zhaopeng
 * @date 2023/12/6 19:41
 **/
public class PasswordComplexityRuleInvalidException extends PasswordInvalidException {

    @Serial
    private static final long serialVersionUID = 2081243783973227401L;

    public PasswordComplexityRuleInvalidException(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordComplexityRuleInvalidException(String msg) {
        super(msg, HttpStatus.BAD_REQUEST);
    }

    public PasswordComplexityRuleInvalidException(String msg, HttpStatus status) {
        super(msg, status);
    }

    public PasswordComplexityRuleInvalidException(String error, String description, HttpStatus status) {
        super(error, description, status);
    }

    public PasswordComplexityRuleInvalidException(Throwable cause, String error, String description, HttpStatus status) {
        super(cause, error, description, status);
    }

}
