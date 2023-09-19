package cn.com.mockingbird.webmvc.model;

import cn.com.mockingbird.webmvc.enums.ResponseCode;
import lombok.Data;

import java.time.Instant;

/**
 * 响应对象模型
 *
 * @author zhaopeng
 * @date 2023/9/19 22:22
 **/
@Data
public class ResponseData<T> {

    /**
     * 响应状态
     */
    private int status;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 接口响应时间戳
     */
    private long timestamp;

    public ResponseData() {
        this.timestamp = Instant.now().toEpochMilli();
    }

    public static <T> ResponseData<T> success(T data) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setStatus(ResponseCode.OK.getCode());
        responseData.setMessage(ResponseCode.OK.getMessage());
        responseData.setData(data);
        return responseData;
    }

    public static <T> ResponseData<T> failure(int code, String message) {
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setStatus(code);
        responseData.setMessage(message);
        return responseData;
    }
}
