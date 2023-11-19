package cn.com.mockingbird.robin.web.mvc;

import cn.com.mockingbird.robin.common.util.response.ResponseCode;
import lombok.Data;

import java.time.Instant;

/**
 * 响应数据模型
 *
 * @author zhaopeng
 * @date 2023/9/19 22:22
 **/
@Data
public class ResponseData<T> {

    /**
     * 响应状态码
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
