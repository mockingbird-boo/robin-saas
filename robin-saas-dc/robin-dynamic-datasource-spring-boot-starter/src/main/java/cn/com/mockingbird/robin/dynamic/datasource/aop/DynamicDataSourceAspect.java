package cn.com.mockingbird.robin.dynamic.datasource.aop;

import cn.com.mockingbird.robin.dynamic.datasource.core.DynamicRoutingDataSource;
import cn.com.mockingbird.robin.dynamic.datasource.util.DataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

/**
 * 动态数据源 AOP 切面
 *
 * @author zhaopeng
 * @date 2023/11/12 19:21
 **/
@Slf4j
@Aspect
@Order(Integer.MAX_VALUE - 100)
public class DynamicDataSourceAspect {

    private final DynamicRoutingDataSource dynamicRoutingDataSource;

    public DynamicDataSourceAspect(DynamicRoutingDataSource dynamicRoutingDataSource) {
        this.dynamicRoutingDataSource = dynamicRoutingDataSource;
    }

    @Before("@annotation(cn.com.mockingbird.robin.dynamic.datasource.aop.DataSource)")
    public void beforeTargetMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DataSource dataSourceAnno = signature.getMethod().getAnnotation(DataSource.class);
        // 如果目标方法上使用了 DataSource 注解，并指定了 DataSource Key，按指定的 Key 进行数据源切换
        if (dataSourceAnno != null && StringUtils.hasText(dataSourceAnno.key())) {
            String dataSourceKey = dataSourceAnno.key();
            switchDataSource(dataSourceKey);
            return;
        }
        // 如果目标类上使用了 DataSource 注解，并指定了 DataSource Key，按指定的 Key 进行数据源切换
        Class<?> targetClass = joinPoint.getTarget().getClass();
        dataSourceAnno = targetClass.getAnnotation(DataSource.class);
        if (dataSourceAnno != null && StringUtils.hasText(dataSourceAnno.key())) {
            String dataSourceKey = dataSourceAnno.key();
            switchDataSource(dataSourceKey);
            return;
        }
        // 如果目标类或目标方法使用了 DataSource 注解，但未指定 DataSource Key，则从 DataSourceContext 中获取 Key 并进行数据源切换
        String dataSourceKey = DataSourceContext.dataSourceKey();
        if (StringUtils.hasText(dataSourceKey)) {
            switchDataSource(dataSourceKey);
        } else {
            throw new RuntimeException("Unknown data source.");
        }
    }

    private void switchDataSource(String key) {
        if (!dynamicRoutingDataSource.switchDataSource(key)) {
            throw new RuntimeException("The data source does not exist.");
        }
    }

}
