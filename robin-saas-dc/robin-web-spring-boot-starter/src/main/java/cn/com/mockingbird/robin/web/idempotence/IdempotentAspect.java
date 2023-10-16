package cn.com.mockingbird.robin.web.idempotence;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * 幂等切面
 *
 * @author zhaopeng
 * @date 2023/10/16 22:58
 **/
@Slf4j
@Aspect
public class IdempotentAspect {

    /**
     * 幂等切点
     * @param idempotent 幂等注解
     */
    @Pointcut("@annotation(idempotent)")
    public void idempotentPointCut(Idempotent idempotent) {

    }

    @Around(value = "idempotentPointCut(idempotent)", argNames = "joinPoint,idempotent")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        log.info("幂等调用之前");
        Object obj = joinPoint.proceed();
        Idempotent.Strategy strategy = idempotent.strategy();
        // TODO TOKEN PARAM 两种方式防重
        return obj;
    }

}
