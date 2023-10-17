package cn.com.mockingbird.robin.web.traceMethod;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.event.Level;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 方法追踪切面
 *
 * @author zhaopeng
 * @date 2023/10/3 15:26
 **/
@Slf4j
@Aspect
@Order(0)
public class TraceAspect {

    @SuppressWarnings("all")
    @Pointcut("@annotation(cn.com.mockingbird.robin.web.traceMethod.Trace)")
    public void tracePointCut() {}

    /**
     * 环绕增强（可以控制目标方法的执行时机）
     *
     * ProceedingJoinPoint.proceed 用于执行目标方法
     * @param joinPoint 切入点
     * @return 目标方法的执行结果
     */
    @SuppressWarnings("all")
    @Around("tracePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        String path = sourceRoot(joinPoint);
        Trace trace = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Trace.class);
        Level level = trace.level();
        String description = trace.description();
        // 执行目标方法获取耗时
        MethodTracker.TrackingData trackingData = MethodTracker.run((MethodTracker.Supplier) joinPoint::proceed);
        // 记录日志
        recordLog(trackingData, path, level, description);
        return trackingData.getResult();
    }



    /**
     * 日志记录
     * @param trackingData 追踪数据
     * @param path 类路径信息
     * @param level 日志级别
     * @param description 描述
     */
    private void recordLog(MethodTracker.TrackingData<?> trackingData, String path, Level level, String description) {
        String logInfo = StringUtils.isBlank(description) ?
                "方法追踪 ==> [{}]，耗时：[{}] ms，开始时间：[{}]，结束时间：[{}]" :
                "方法追踪 ==> [{}]，[{}] 耗时：[{}] ms，开始时间：[{}]，结束时间：[{}]";

        String start = cn.com.mockingbird.robin.common.util.StringUtils.millis2String(trackingData.getStartMillis());
        String end = cn.com.mockingbird.robin.common.util.StringUtils.millis2String(trackingData.getEndMillis());
        List<String> params = new ArrayList<>();
        params.add(path);
        if (StringUtils.isNotBlank(description)) {
            params.add(description);
        }
        params.add(String.valueOf(trackingData.getDuration()));
        params.add(start);
        params.add(end);
        switch (level) {
            case TRACE -> log.trace(logInfo, params.toArray());
            case DEBUG -> log.debug(logInfo, params.toArray());
            case INFO -> log.info(logInfo, params.toArray());
            case WARN -> log.warn(logInfo, params.toArray());
            case ERROR -> log.error(logInfo, params.toArray());
        }
    }

    /**
     * 获取方法类路径信息
     * @param joinPoint 切点
     * @return 方法类路径信息
     */
    private String sourceRoot(ProceedingJoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();

        StringBuilder source = new StringBuilder();
        source.append(className).append(".").append(methodName).append("(");

        Map<String, String> params = getMethodParams(signature);
        if (!params.isEmpty()) {
            AtomicInteger size = new AtomicInteger(params.size());
            params.forEach((k, v) -> source.append(v).append(" ").append(k).append(size.decrementAndGet() > 0 ? ", " : ""));
        }
        source.append(")");
        return source.toString();
    }

    private static Map<String, String> getMethodParams(MethodSignature signature) {
        Map<String, String> params = new LinkedHashMap<>();
        String[] parameterNames = signature.getParameterNames();
        Class<?>[] parameterTypes = signature.getParameterTypes();
        if (parameterNames != null && parameterTypes != null && parameterNames.length > 0 && parameterTypes.length > 0) {
            for (int i = 0; i < Math.min(parameterNames.length, parameterTypes.length); i++) {
                params.put(parameterNames[i], parameterTypes[i].getSimpleName());
            }
        }
        return params;
    }

}
