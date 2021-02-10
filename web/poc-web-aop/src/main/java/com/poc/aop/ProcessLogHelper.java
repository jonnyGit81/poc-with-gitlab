package com.poc.aop;

import com.poc.support.dto.MsgInfo;
import com.poc.support.util.LogUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;

public final class ProcessLogHelper {
    private ProcessLogHelper(){}

    public static Object processLog(Logger log, ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object returnProceed = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        LogUtil.INFO.apply(log,
                MsgInfo.of("==> Time Taken by {} ==> {}ms", joinPoint, timeTaken));
        return returnProceed;
    }
}
