package cn.com.mockingbird.robin.web.advice;

import cn.com.mockingbird.robin.common.constant.Standard;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 参数绑定增强
 * <p>
 * 实现了GET请求及POST请求使用FormData传参时，
 * 日期时间字符串参数转Java日期时间类型的统一处理
 *
 * @author zhaopeng
 * @date 2023/10/15 2:29
 **/
@ControllerAdvice
public class InitBinderAdvice {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDate.parse(text, DateTimeFormatter.ofPattern(Standard.DateTimePattern.DATE)));
            }
        });
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(LocalDateTime.parse(text, DateTimeFormatter.ofPattern(Standard.DateTimePattern.DATETIME)));
            }
        });
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    setValue(DateUtils.parseDate(text, Standard.DateTimePattern.DATETIME));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
