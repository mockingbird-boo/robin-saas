package cn.com.mockingbird.robin.web.trace.method;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeUnit;

/**
 * 测试方法追踪注解
 *
 * @see Trace
 * @author zhaopeng
 * @date 2023/11/19 22:45
 **/
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = {TestTraceAnnotation.class, TestTraceAnnotation.AopConfig.class})
public class TestTraceAnnotation {

    @Configuration
    @EnableAspectJAutoProxy
    @EnableAutoConfiguration
    @Import(AutoConfigurationImportSelector.class)
    static class AopConfig {

        /*
        这样配置也可以
        @Bean
        public TraceAspect traceAspect() {
            return new TraceAspect();
        }
        */

    }

    @Lazy
    @Autowired
    TestTraceAnnotation testTraceAnnotation;

    @SpyBean
    TraceAspect traceAspect;

    @BeforeEach
    void beforeTest() {
        System.out.println("************* 测试开始 *************");
    }

    @AfterEach
    void afterTest() {
        System.out.println("************* 测试结束 *************");
    }

    @Trace
    void targetMethod() {
        System.out.println("目标方法开始工作......");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("目标方法完成工作......");
    }

    @Test
    void test() {
        testTraceAnnotation.targetMethod();
    }

}
