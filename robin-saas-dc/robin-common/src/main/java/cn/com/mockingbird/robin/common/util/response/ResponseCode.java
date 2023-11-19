package cn.com.mockingbird.robin.common.util.response;

import lombok.Getter;

/**
 * 响应码枚举
 *
 * @author zhaopeng
 * @date 2023/9/19 22:36
 **/
@Getter
public enum ResponseCode {

    /**
     * 接口响应成功
     */
    OK(200, "success"),

    /**
     * 请求参数校验失败
     */
    BAD_REQUEST(400, "请求参数校验失败"),

    /**
     * 请求冲突
     */
    CONFLICT(409, "请求冲突"),

    /**
     * 请求方法不被允许
     */
    METHOD_NOT_ALLOWED(405, "请求方法不被允许"),

    /**
     * 服务器处理失败
     */
    FAIL(500, "fail"),

    /**
     * 服务器无法完成请求
     */
    NOT_IMPLEMENTED(501, "服务器无法完成请求"),

    ACCESS_DENIED(403, "权限不足")
    ;

    /**
     * 响应码
     */
    private final int code;

    /**
     * 响应码描述
     */
    @Getter
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
