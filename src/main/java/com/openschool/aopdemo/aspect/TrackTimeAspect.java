package com.openschool.aopdemo.aspect;

import com.openschool.aopdemo.service.TrackTimeLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class TrackTimeAspect {
    private final TrackTimeLogService trackTimeLogService;

    @Pointcut("@annotation(com.openschool.aopdemo.annotation.TrackTime)")
    public void syncRunningPointcut() {
    }

    @Pointcut("@annotation(com.openschool.aopdemo.annotation.TrackAsyncTime)")
    public void asyncRunningPointcut() {
    }

    @Around("syncRunningPointcut()")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        return trackExecutionTime(joinPoint, false);
    }

    @Around("asyncRunningPointcut()")
    public Object trackAsyncTime(ProceedingJoinPoint joinPoint) {
        CompletableFuture<Object> futureResult = CompletableFuture.supplyAsync(() -> {
            try {
                return trackExecutionTime(joinPoint, true);
            } catch (Throwable e) {
                log.error("Exception in async method {}: {}", joinPoint.getSignature().getName(), e.getMessage());
                throw new RuntimeException(e);
            }
        });

        return futureResult.join();
    }

    private Object trackExecutionTime(ProceedingJoinPoint joinPoint, boolean isAsyncCall) throws Throwable {
        LocalDateTime timestamp = LocalDateTime.now();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = className.substring(className.lastIndexOf('.') + 1) + "." + joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        log.info("TrackTime call for {} {}", methodName, methodArgs);

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;

        trackTimeLogService.create(methodName, methodArgs, isAsyncCall, timestamp, timeTaken);

        return result;
    }
}
