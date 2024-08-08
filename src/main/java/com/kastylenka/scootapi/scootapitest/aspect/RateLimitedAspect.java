package com.kastylenka.scootapi.scootapitest.aspect;

import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@ConditionalOnProperty(value = "aspect.rate-limited.enabled")
@Component
@RequiredArgsConstructor
public class RateLimitedAspect {

    private final RateLimitingService rateLimitingService;

    @Around("@annotation(rateLimitedAnnotation)")
    public Object checkForLimit(ProceedingJoinPoint joinPoint, RateLimited rateLimitedAnnotation) throws Throwable {
        String apiKey = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        RateLimiter rateLimiter = rateLimitingService.getRateLimiter(rateLimitedAnnotation.name(), apiKey);
        boolean allowed = rateLimiter.acquirePermission();
        if (!allowed) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS);
        }
        return joinPoint.proceed();
    }
}
