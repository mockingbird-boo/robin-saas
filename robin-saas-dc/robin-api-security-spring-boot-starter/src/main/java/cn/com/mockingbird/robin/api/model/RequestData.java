package cn.com.mockingbird.robin.api.model;

import lombok.Data;

/**
 * 请求数据模型
 *
 * @author zhaopeng
 * @date 2023/10/11 22:47
 **/
@Data
public class RequestData<T> {
    private String ip;
    private String url;
    private String method;
    private String clazz;
    private String function;
    private T param;
}
