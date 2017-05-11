package com.zm.web.configuration.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceAop {
    private static Logger logger = LoggerFactory.getLogger(ServiceAop.class);

   private ThreadLocal<Long> timeLocal = new ThreadLocal<Long>();

    @Pointcut("execution(public * com.zm.web.service.*.*(..))")
    public void webRequestLog() {}

    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        timeLocal.set(System.currentTimeMillis());
    }

    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        long startTime = timeLocal.get();
        logger.info("花费的时间为:"+(System.currentTimeMillis()-startTime)+"毫秒");
    }

    @Around("webRequestLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object o = null;

        o = pjp.proceed();

        return o;
    }
}