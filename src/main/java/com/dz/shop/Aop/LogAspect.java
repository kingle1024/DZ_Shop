package com.dz.shop.Aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.dz.shop..*.*(..))")
    public void publicTarget() { }

    @Around("publicTarget()")
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info(joinPoint.getSignature().toLongString());


        Arrays.asList(joinPoint.getArgs()).forEach(arg -> logger.info("[Arg]: " + arg));
        Object result = joinPoint.proceed();
        logger.info("[return] " + result);
        return result;
    }
}