package cn.com.mockingbird.robin.common.exception;

import lombok.Getter;

/**
 * 异常基类
 *
 * @author zhaopeng
 * @date 2023/10/18 0:42
 **/
@Getter
@SuppressWarnings({"serial", "unused"})
public abstract class BaseRuntimeException extends RuntimeException {

    private int code = 500;

    public BaseRuntimeException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BaseRuntimeException(String message) {
        super(message);
    }

}
