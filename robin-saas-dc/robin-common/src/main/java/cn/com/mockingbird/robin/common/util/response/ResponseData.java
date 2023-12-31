package cn.com.mockingbird.robin.common.util.response;

import lombok.Data;
import org.springframework.util.Assert;

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

    public static <T> boolean isSuccess(ResponseData<T> responseData) {
        return responseData.status == ResponseCode.OK.getCode();
    }

    public static <T> boolean hasData(ResponseData<T> responseData) {
        Assert.notNull(responseData, "responseData requires not null");
        return isSuccess(responseData) && null != responseData.getData();
    }
}
