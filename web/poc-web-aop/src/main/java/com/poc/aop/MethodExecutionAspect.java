package com.poc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class MethodExecutionAspect {
    private static final Logger LOG = LoggerFactory.getLogger(MethodExecutionAspect.class);

    @Around("com.poc.aop.CommonJoinPointConfig.repositoryExecution()")
    public Object repositoryAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return ProcessLogHelper.processLog(LOG, joinPoint);
    }

    @Around("com.poc.aop.CommonJoinPointConfig.serviceExecution()")
    public Object serviceAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return ProcessLogHelper.processLog(LOG, joinPoint);
    }

    @Around("com.poc.aop.CommonJoinPointConfig.controllerExecution()")
    public Object controllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return ProcessLogHelper.processLog(LOG, joinPoint);
    }

    @Around("com.poc.aop.CommonJoinPointConfig.securityExecution()")
    public Object securityAround(ProceedingJoinPoint joinPoint) throws Throwable {
        return ProcessLogHelper.processLog(LOG, joinPoint);
    }
}
