package com.example.orchestrator.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggerAspect {
    @Pointcut("@annotation(com.example.orchestrator.aspect.annotation.AspectLogger)")
    public void annotationPointcut() {
    }
    @Before(value = "annotationPointcut()")
    public void beforeLogger(JoinPoint joinPoint) {
        org.slf4j.LoggerFactory.getLogger(joinPoint.getTarget().getClass()).info("Method exec: " + MethodSignature.class.cast(joinPoint.getSignature()).getMethod().getName());
    }

}