package cn.com.mockingbird.robin.web.config;

import cn.com.mockingbird.robin.common.constant.Standard;
import cn.com.mockingbird.robin.web.context.SpringApplicationContext;
import cn.com.mockingbird.robin.web.mvc.InitBinderAdvice;
import cn.com.mockingbird.robin.web.mvc.ResponseDataAdvice;
import cn.com.mockingbird.robin.web.mvc.UniformExceptionHandler;
import cn.com.mockingbird.robin.web.trace.FeignRequestInterceptor;
import cn.com.mockingbird.robin.web.trace.RequestTraceFilter;
import cn.com.mockingbird.robin.web.trace.method.TraceAspect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Web 自动配置类
 *
 * @author zhaopeng
 * @date 2023/10/15 2:05
 **/
@AutoConfiguration
public class WebAutoConfiguration {

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    public ResponseDataAdvice responseDataAdvice(ObjectMapper objectMapper) {
        return new ResponseDataAdvice(objectMapper);
    }

    @Bean
    public UniformExceptionHandler uniformExceptionHandler() {
        return new UniformExceptionHandler();
    }

    @Bean
    @ConditionalOnBean(UniformExceptionHandler.class)
    public InitBinderAdvice initBinderAdvice() {
        return new InitBinderAdvice();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Standard.DateTimePattern.DATETIME)));
            jacksonObjectMapperBuilder.serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(Standard.DateTimePattern.DATE)));
            jacksonObjectMapperBuilder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Standard.DateTimePattern.DATETIME)));
            jacksonObjectMapperBuilder.deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(Standard.DateTimePattern.DATE)));
        };
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.web.enhance.trace", name = "enable", havingValue = "true", matchIfMissing = true)
    public FilterRegistrationBean<RequestTraceFilter> requestTraceFilter() {
        FilterRegistrationBean<RequestTraceFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new RequestTraceFilter());
        filterRegistrationBean.setOrder(Integer.MIN_VALUE + 100);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean
    public FeignRequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }

    @Bean
    public TraceAspect traceAspect() {
        return new TraceAspect();
    }

}
