package cn.com.mockingbird.robin.iam.support.result;

import cn.com.mockingbird.robin.iam.support.exception.ExceptionStatus;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * API Rest 结果封装类
 *
 * @author zhaopeng
 * @date 2023/12/7 19:36
 **/
public class ApiRestResult<T> implements Serializable {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    @Serial
    private static final long serialVersionUID = -5396280450442040415L;
    /**
     * 状态
     */
    private String status;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回内容
     */
    @Getter
    private T result;
    /**
     * 是否成功
     */
    @Getter
    private final Boolean success;

    ApiRestResult(String status, String message, T result) {
        this.status = status;
        this.result = result;
        this.success = status.equals(String.valueOf(HttpStatus.OK.value()));
        if (StringUtils.isNotBlank(message)) {
            this.message = message;
        } else {
            this.message = status.equals(String.valueOf(HttpStatus.OK.value())) ? SUCCESS : FAIL;
        }

    }

    public static <T> ApiRestResult<T> ok(T data) {
        return new ApiRestResult<>(String.valueOf(HttpStatus.OK.value()), SUCCESS, data);
    }

    public static <T> ApiRestResult<T> ok() {
        return new ApiRestResult<>(String.valueOf(HttpStatus.OK.value()), SUCCESS, null);
    }

    public static <T> ApiRestResult<T> err() {
        return new ApiRestResult<>(ExceptionStatus.EX900001.getCode(), ExceptionStatus.EX900001.getMessage(), null);
    }

    public static <T> ApiRestResult<T> err(T data) {
        return new ApiRestResult<>(ExceptionStatus.EX900001.getCode(), ExceptionStatus.EX900001.getMessage(), data);
    }

    public static <T> ApiRestResult<T> err(String msg, String code) {
        return new ApiRestResult<>(code, msg, null);
    }

    public static <T> RestResultBuilder<T> builder() {
        return new RestResultBuilder();
    }

    public ApiRestResult<T> result(T result) {
        this.result = result;
        return this;
    }

    public String getStatus() {
        return StringUtils.isBlank(this.status) ? String.valueOf(HttpStatus.OK.value()) : this.status;
    }

    public ApiRestResult<T> status(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        if (StringUtils.isBlank(this.message)) {
            return this.success ? SUCCESS : FAIL;
        } else {
            return this.message;
        }
    }

    public ApiRestResult<T> message(String msg) {
        this.message = msg;
        return this;
    }

    public ApiRestResult<T> build() {
        return new ApiRestResult<>(this.status, this.message, this.result);
    }

    public static class RestResultBuilder<T> {
        private String status;
        private String message;
        private T result;

        RestResultBuilder() {
        }

        public RestResultBuilder<T> status(String status) {
            this.status = status;
            return this;
        }

        public RestResultBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public RestResultBuilder<T> result(T result) {
            this.result = result;
            return this;
        }

        public ApiRestResult<T> build() {
            if (ObjectUtils.isEmpty(this.status)) {
                this.status = Integer.toString(HttpStatus.OK.value());
            }

            if (!StringUtils.equals(this.status, String.valueOf(HttpStatus.OK.value())) && ObjectUtils.isEmpty(this.message)) {
                this.message = HttpStatus.OK.getReasonPhrase();
            }

            if (this.result instanceof Boolean && this.result.equals(Boolean.FALSE)) {
                this.message = FAIL;
            }

            return new ApiRestResult<>(this.status, this.message, this.result);
        }

        public String toString() {
            return JSONObject.toJSONString(this, new JSONWriter.Feature[0]);
        }
    }
}
