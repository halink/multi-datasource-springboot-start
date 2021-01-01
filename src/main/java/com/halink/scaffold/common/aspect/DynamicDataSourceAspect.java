package com.halink.scaffold.common.aspect;

import com.halink.scaffold.common.annotation.DBConnection;
import com.halink.scaffold.common.enumerate.DataSourceTypeEnum;
import com.halink.scaffold.config.multidatasource.DynamicDataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 动态切换数据源
 *
 * @author halink
 * @date 2020-12-31 22:16
 */
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {

    @Pointcut("@within(com.halink.scaffold.common.annotation.DBConnection)")
    public void dataSourcePointCut() {
    }


    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        DataSourceTypeEnum dsKey = this.getAnnotation(joinPoint).value();
        DynamicDataSourceContextHolder.setContextKey(dsKey);
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSourceContextHolder.removeContextKey();
        }
    }

    /**
     * 根据类或方法获取数据源注解
     */
    private DBConnection getAnnotation(ProceedingJoinPoint joinPoint) {
        Class<?> targetClass = joinPoint.getTarget().getClass().getInterfaces()[0];
        DBConnection dsAnnotation = targetClass.getAnnotation(DBConnection.class);
        if (Objects.nonNull(dsAnnotation)) {
            return dsAnnotation;
        } else {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            return methodSignature.getMethod().getAnnotation(DBConnection.class);
        }
    }
}
