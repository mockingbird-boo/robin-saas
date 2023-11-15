package cn.com.mockingbird.robin.redis.core.lock;

import cn.com.mockingbird.robin.common.exception.BaseRuntimeException;

import java.io.Serial;

/**
 * 加锁异常
 *
 * @author zhaopeng
 * @date 2023/11/15 15:56
 **/
public class LockRuntimeException extends BaseRuntimeException {

    @Serial
    private static final long serialVersionUID = -8865299353540981790L;

    public LockRuntimeException(String message) {
        super(message);
    }
}
