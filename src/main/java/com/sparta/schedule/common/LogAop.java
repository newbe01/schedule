package com.sparta.schedule.common;

import java.lang.reflect.Method;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

//@Aspect
@Slf4j
//@Component
public class LogAop {

    @Pointcut("execution(* com.sparta.schedule.controller..*.*(..))")
    private void cut() {}

    @Before("cut()")
    public void before(JoinPoint joinPoint) {
        //실행되는 함수 이름을 가져오고 출력
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        log.info("start method : {}", method.getName());

        //메서드에 들어가는 매개변수 배열을 읽어옴
        Object[] args = joinPoint.getArgs();

        //매개변수 배열의 종류와 값을 출력
        for(Object obj : args) {
            log.info("type : {}", obj.getClass().getSimpleName());
            log.info("value : {}", obj);
        }
    }

    //cut() 메서드가 종료되는 시점에 afterReturn() 메서드 실행
    //@AfterReturning 어노테이션의 returning 값과 afterReturn 매개변수 obj의 이름이 같아야 함
    @AfterReturning(value = "cut()", returning = "obj")
    public void afterReturn(JoinPoint joinPoint, Object obj) {
        log.info("return : {}", obj);
    }

}
