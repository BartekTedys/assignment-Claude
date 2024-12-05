package com.example.assignment_Claude.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {
    @Before("execution(* com.example.assignment_Claude.services.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        log.info("Entering method: {} with arguments: {}",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    @AfterReturning(
            pointcut = "execution(* com.example.assignment_Claude.services.*.*(..))",
            returning = "result"
    )
    public void logMethodExit(JoinPoint joinPoint, Object result) {
        log.info("Exiting method: {} with result: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    @AfterThrowing(
            pointcut = "execution(* com.example.assignment_Claude.services.*.*(..))",
            throwing = "exception"
    )
    public void logMethodException(JoinPoint joinPoint, Exception exception) {
        log.error("Exception in method: {} with error: {}",
                joinPoint.getSignature().toShortString(),
                exception.getMessage());
    }
}