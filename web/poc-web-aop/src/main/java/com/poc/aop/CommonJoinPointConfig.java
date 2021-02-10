package com.poc.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("execution(* com.poc.db.repository.*.*(..))")
    public void repositoryExecution(){}

    @Pointcut("execution(* com.poc.db.service.*.*(..))")
    public void serviceExecution(){}

    @Pointcut("@annotation(com.poc.aop.TrackTime)")
    public void trackTimeAnnotation(){}

    @Pointcut("execution(* com.poc.web.*.*(..))")
    public void controllerExecution(){}


    @Pointcut("execution(* com.poc.security.*.*(..))")
    public void securityExecution(){}
}
