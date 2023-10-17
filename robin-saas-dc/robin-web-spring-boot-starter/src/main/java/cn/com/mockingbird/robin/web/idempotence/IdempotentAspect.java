package cn.com.mockingbird.robin.web.idempotence;

import cn.com.mockingbird.robin.common.constant.Standard;
import cn.com.mockingbird.robin.common.user.UserHolder;
import cn.com.mockingbird.robin.common.util.BranchUtils;
import cn.com.mockingbird.robin.redis.core.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * 幂等切面
 *
 * @author zhaopeng
 * @date 2023/10/16 22:58
 **/
@Slf4j
@Aspect
public class IdempotentAspect {

    private final RedisService redisService;

    public IdempotentAspect(RedisService redisService) {
        this.redisService = redisService;
    }


    /**
     * 幂等切点
     * @param idempotent 幂等注解
     */
    @SuppressWarnings("unused")
    @Pointcut("@annotation(idempotent)")
    public void idempotentPointCut(Idempotent idempotent) {

    }

    @Around(value = "idempotentPointCut(idempotent)", argNames = "joinPoint,idempotent")
    public Object around(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        // Http 请求
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 用户名
        String username = UserHolder.getCurrentUser().getUsername();
        // 幂等策略
        Idempotent.Strategy strategy = idempotent.strategy();
        Boolean isPrettyRequest = (Boolean) BranchUtils.isTureOrFalseWithReturn(strategy == Idempotent.Strategy.TOKEN).trueOrFalseHandle(() -> {
            // 从请求头中获取幂等令牌
            String token = request.getHeader(Standard.RequestHeader.IDEMPOTENT_TOKEN);
            // 如果没有幂等令牌，拒绝请求，提示报错
            if (StringUtils.isBlank(token)) {
                throw new ValidationException("幂等性TOKEN不能为空");
            }
            // 有幂等令牌就删，成功删掉即非重复提交
            String key = Key.generateTokenKey(username, token);
            return redisService.delete(key) == 1;
        }, () -> false);
        if (isPrettyRequest) {
            return joinPoint.proceed();
        }
        throw new IdempotencyException(idempotent.message());
    }

}
