package cn.com.mockingbird.robin.iam.support.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * HttpResponse 工具类
 *
 * @author zhaopeng
 * @date 2023/12/7 3:30
 **/
@UtilityClass
public class HttpResponseUtils {

    private final String NULL = "null";
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public void flushResponse(HttpServletResponse response, String responseContent) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            if (StringUtils.isNoneBlank(responseContent)) {
                writer.write(responseContent);
            } else {
                writer.write("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void flushResponseJson(HttpServletResponse response, int status, Object data) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(status);
            PrintWriter writer = response.getWriter();
            if (ObjectUtils.isNotEmpty(data)) {
                String value = OBJECT_MAPPER.writeValueAsString(data);
                writer.write(value);
            } else {
                writer.write("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void flushResponseJson(HttpServletResponse response, int status, ObjectMapper objectMapper, Object data) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(status);
            PrintWriter writer = response.getWriter();
            if (ObjectUtils.isNotEmpty(data)) {
                String value = objectMapper.writeValueAsString(data);
                writer.write(value);
            } else {
                writer.write("");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
