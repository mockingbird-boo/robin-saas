package cn.com.mockingbird.robin.web.idempotence;

import cn.com.mockingbird.robin.common.exception.BaseRuntimeException;
import cn.com.mockingbird.robin.common.util.response.ResponseCode;

import java.io.Serial;

/**
 * 幂等性异常
 *
 * @author zhaopeng
 * @date 2023/10/18 0:33
 **/
@SuppressWarnings("unused")
public class IdempotencyException extends BaseRuntimeException {

    @Serial
    private static final long serialVersionUID = -434493474168017297L;

    public IdempotencyException(String message) {
        super(ResponseCode.CONFLICT.getCode(), message);
    }

    public IdempotencyException() {
        super(ResponseCode.CONFLICT.getCode(), ResponseCode.CONFLICT.getMessage());
    }

}
