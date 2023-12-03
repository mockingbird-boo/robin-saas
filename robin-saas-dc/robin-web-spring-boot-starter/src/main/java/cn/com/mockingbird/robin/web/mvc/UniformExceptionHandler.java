package cn.com.mockingbird.robin.web.mvc;

import cn.com.mockingbird.robin.common.exception.BaseRuntimeException;
import cn.com.mockingbird.robin.common.util.response.ResponseCode;
import cn.com.mockingbird.robin.common.util.response.ResponseData;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 统一的异常处理器
 *
 * @author zhaopeng
 * @date 2023/9/19 23:34
 **/
@Slf4j
@RestControllerAdvice
public class UniformExceptionHandler {

    /**
     * 处理 {@link BaseRuntimeException} 异常
     * @param e 异常实例
     * @return 使用 {@link ResponseData} 封装的异常响应实例
     */
    @ExceptionHandler(BaseRuntimeException.class)
    public ResponseData<String> handleBaseRuntimeException(BaseRuntimeException e, HttpServletResponse response) {
        response.setStatus(e.getCode());
        return ResponseData.failure(e.getCode(), e.getMessage());
    }

    /**
     * 处理 {@link IllegalArgumentException} 异常
     * 使用 {@link org.springframework.util.Assert} 断言失败时就会抛出该异常
     * @param e IllegalArgumentException 实例
     * @return 使用 {@link ResponseData} 封装的异常响应实例
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseData<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseData.failure(ResponseCode.FAIL.getCode(), e.getMessage());
    }

    /**
     * 单独拦截参数校验的三个异常：
     * {@link MethodArgumentNotValidException}
     * {@link ConstraintViolationException}
     * {@link BindException}
     * @param e 校验异常
     * @return 使用 {@link ResponseData} 封装的异常响应实例
     */
    @ExceptionHandler(value = {BindException.class, ValidationException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<String> handleValidatedException(Exception e) {
        ResponseData<String> responseData;
        if (e instanceof MethodArgumentNotValidException ex) {
            responseData = ResponseData.failure(ResponseCode.BAD_REQUEST.getCode(),
                    ex.getBindingResult().getAllErrors().stream()
                            .map(ObjectError::toString)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof ConstraintViolationException ex) {
            responseData = ResponseData.failure(ResponseCode.BAD_REQUEST.getCode(),
                    ex.getConstraintViolations().stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining("; "))
            );
        } else if (e instanceof BindException ex) {
            responseData = ResponseData.failure(ResponseCode.BAD_REQUEST.getCode(),
                    ex.getAllErrors().stream()
                            .map(ObjectError::toString)
                            .collect(Collectors.joining("; "))
            );
        } else {
            responseData = ResponseData.failure(ResponseCode.BAD_REQUEST.getCode(), ResponseCode.BAD_REQUEST.getMessage());
        }
        return responseData;
    }


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
