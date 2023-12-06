package cn.com.mockingbird.robin.iam.support.web.log;

import cn.com.mockingbird.robin.iam.support.web.useragent.UserAgent;
import com.alibaba.fastjson2.JSONObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * 日志模型
 *
 * @author zhaopeng
 * @date 2023/12/5 19:22
 **/
@Getter
@Setter
@EqualsAndHashCode
public class LogInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 8971252908481728174L;

    /**
     * IP 地址
     */
    private String ip;

    private Object result;

    private String requestUrl;

    private String httpType;

    private String body;

    private String method;

    private Map<String, String> headers;

    private Object parameter;

    private Boolean success;

    private UserAgent userAgent;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

}
