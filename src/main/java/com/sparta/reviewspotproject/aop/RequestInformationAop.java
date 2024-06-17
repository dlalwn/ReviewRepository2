package com.sparta.reviewspotproject.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j(topic = "Request Information")
@Aspect
@Component
public class RequestInformationAop {
    @Pointcut("execution(* com.sparta.reviewspotproject.controller.*.*(..))")
    public void controller(){
    }

    @Before("controller()")
    public void logInfo(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null){
            HttpServletRequest request = attributes.getRequest();

            log.info("Request URL : {}, HTTP Method :{}", request.getRequestURI(), request.getMethod());
        }
    }
}
