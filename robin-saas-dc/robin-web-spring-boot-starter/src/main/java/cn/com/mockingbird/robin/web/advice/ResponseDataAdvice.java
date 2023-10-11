package cn.com.mockingbird.robin.web.advice;

import cn.com.mockingbird.robin.web.model.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 响应数据增强
 *
 * @author zhaopeng
 * @date 2023/9/19 22:50
 **/
@RestControllerAdvice
public class ResponseDataAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    public ResponseDataAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 是否支持增强
     * @param returnType the return type
     * @param converterType the selected converter type
     * @return true - 支持；false - 不支持
     */
    @SuppressWarnings("all")
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SuppressWarnings("all")
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // RestController 方法如果返回 String 类型会直接返回 String 值，于是在这里使用 ResponseData 进行封装
        if (body instanceof String) {
            return objectMapper.writeValueAsString(ResponseData.success(body));
        }
        // 已经使用 ResponseData 封装过的 body 直接返回
        if (body instanceof ResponseData) {
            return body;
        }

        return ResponseData.success(body);
    }
}
