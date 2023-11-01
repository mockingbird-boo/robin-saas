package cn.com.mockingbird.robin.api.aspect;

import cn.com.mockingbird.robin.api.annotation.ApiSecurity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * ApiSecurity 注解的切面
 *
 * @author zhaopeng
 * @date 2023/10/31 2:13
 **/
@Slf4j
@Aspect
public class ApiSecurityAspect {

    @Before(value = "@annotation(apiSecurity)", argNames = "joinPoint,apiSecurity")
    public void beforeApiProcess(JoinPoint joinPoint, ApiSecurity apiSecurity) {

    }

}
