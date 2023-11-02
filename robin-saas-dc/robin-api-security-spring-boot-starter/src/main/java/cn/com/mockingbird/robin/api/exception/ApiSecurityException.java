package cn.com.mockingbird.robin.api.exception;

import cn.com.mockingbird.robin.common.exception.BaseRuntimeException;
import cn.com.mockingbird.robin.web.mvc.ResponseCode;

import java.io.Serial;

/**
 * API 安全校验异常
 *
 * @author zhaopeng
 * @date 2023/11/2 18:01
 **/
@SuppressWarnings("unused")
public class ApiSecurityException extends BaseRuntimeException {

    @Serial
    private static final long serialVersionUID = 7534914546023188120L;

    public ApiSecurityException(int code, String message) {
        super(code, message);
    }

    public ApiSecurityException(String message) {
        super(ResponseCode.NOT_IMPLEMENTED.getCode(), message);
    }

    public ApiSecurityException() {
        super(ResponseCode.NOT_IMPLEMENTED.getCode(), ResponseCode.NOT_IMPLEMENTED.getMessage());
    }

}
