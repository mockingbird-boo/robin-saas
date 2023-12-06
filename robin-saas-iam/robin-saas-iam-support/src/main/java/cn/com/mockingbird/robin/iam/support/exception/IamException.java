package cn.com.mockingbird.robin.iam.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * IAM 运行时异常
 *
 * @author zhaopeng
 * @date 2023/12/6 18:26
 **/
@Getter
@SuppressWarnings("serial")
public class IamException extends RuntimeException {

    public static final String DEFAULT_EXCEPTION = "unknown_exception";
    public static final String CONSTRAINT_VIOLATION_EXCEPTION = "constraint_violation_exception";
    public static final String BIND_EXCEPTION = "bind_exception";
    public static final HttpStatus DEFAULT_STATUS;
    public static final String ERROR = "error";
    public static final String DESCRIPTION = "error_description";
    public static final String STATUS = "status";

    static {
        DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private Map<String, String> additionalInformation;
    private final HttpStatus status;
    private final String error;

    public IamException(String msg, Throwable t) {
        super(msg, t);
        this.additionalInformation = null;
        this.error = "unknown_exception";
        this.status = DEFAULT_STATUS;
    }

    public IamException(String msg) {
        this("unknown_exception", msg, DEFAULT_STATUS);
    }

    public IamException(String msg, HttpStatus status) {
        this("unknown_exception", msg, status);
    }

    public IamException(String error, String description) {
        super(description);
        this.additionalInformation = null;
        this.error = error;
        this.status = DEFAULT_STATUS;
    }

    public IamException(String error, String description, HttpStatus status) {
        super(description);
        this.additionalInformation = null;
        this.error = error;
        this.status = status;
    }

    public IamException(Throwable cause, String error, String description, HttpStatus status) {
        super(description, cause);
        this.additionalInformation = null;
        this.error = error;
        this.status = status;
    }

    public String getErrorCode() {
        return this.error;
    }

    public void addAdditionalInformation(String key, String value) {
        if (this.additionalInformation == null) {
            this.additionalInformation = new TreeMap<>();
        }

        this.additionalInformation.put(key, value);
    }

    public static IamException valueOf(Map<String, String> errorParams) {
        String errorCode = errorParams.get("error");
        String errorMessage = errorParams.getOrDefault("error_description", null);
        HttpStatus status = DEFAULT_STATUS;
        if (errorParams.containsKey("status")) {
            status = HttpStatus.valueOf(Integer.parseInt(errorParams.get("status")));
        }
        IamException ex = new IamException(errorCode, errorMessage, status);
        Set<Map.Entry<String, String>> entries = errorParams.entrySet();
        for (Map.Entry<String, String> stringStringEntry : entries) {
            String key = stringStringEntry.getKey();
            if (!"error".equals(key) && !"error_description".equals(key)) {
                ex.addAdditionalInformation(key, stringStringEntry.getValue());
            }
        }
        return ex;
    }

    @Override
    public String toString() {
        return this.getSummary();
    }

    public String getSummary() {
        StringBuilder builder = new StringBuilder();
        String delim = "";
        String error = this.getErrorCode();
        if (error != null) {
            builder.append(delim).append("error=\"").append(error).append("\"");
            delim = ", ";
        }
        String errorMessage = this.getMessage();
        if (errorMessage != null) {
            builder.append(delim).append("error_description=\"").append(errorMessage).append("\"");
            delim = ", ";
        }
        Map<String, String> additionalParams = this.getAdditionalInformation();
        if (additionalParams != null) {
            for(Iterator<Map.Entry<String, String>> iterator = additionalParams.entrySet().iterator(); iterator.hasNext(); delim = ", ") {
                Map.Entry<String, String> param = iterator.next();
                builder.append(delim).append(param.getKey()).append("=\"").append(param.getValue()).append("\"");
            }
        }
        return builder.toString();
    }

}
