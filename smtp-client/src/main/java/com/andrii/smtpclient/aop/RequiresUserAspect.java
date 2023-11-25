package com.andrii.smtpclient.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Aspect
@Component
public class RequiresUserAspect {
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RequiresUser {
    }
    @Around("@annotation(RequiresUserAspect.RequiresUser)")
    public Object extractUserInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object[] args = joinPoint.getArgs();
        if (attributes != null) {
            long id = Long.parseLong(attributes.getRequest().getHeader("id"));
            args[0] = id;
        }
        return joinPoint.proceed(args);
    }
}
