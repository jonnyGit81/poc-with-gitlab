package com.poc.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class TrackTimeMethodAnnotationExecutionAspect {
    private static final Logger LOG = LoggerFactory.getLogger(TrackTimeMethodAnnotationExecutionAspect.class);

    @Around("com.poc.aop.CommonJoinPointConfig.trackTimeAnnotation()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return ProcessLogHelper.processLog(LOG, joinPoint);
    }
}
