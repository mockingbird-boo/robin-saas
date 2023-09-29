package cn.com.mockingbird.robin.webmvc.exception;

import cn.com.mockingbird.robin.webmvc.enums.ResponseCode;
import cn.com.mockingbird.robin.webmvc.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author zhaopeng
 * @date 2023/9/19 23:34
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 默认的全局异常处理
     * @param e 异常实例
     * @return 使用 ResponseData 封装的异常响应实例
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> handle(Exception e) {
        log.error("ExceptionHandler handle exception:", e);
        return ResponseData.failure(ResponseCode.FAIL.getCode(), e.getMessage());
    }

}
