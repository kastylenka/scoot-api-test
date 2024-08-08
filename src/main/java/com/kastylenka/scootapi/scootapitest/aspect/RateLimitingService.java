package com.kastylenka.scootapi.scootapitest.aspect;

import static java.util.concurrent.TimeUnit.MINUTES;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimitingService {

    private final RateLimiterRegistry rateLimiterRegistry;
    private final ConcurrentHashMap<String, RateLimiter> rateLimiters = new ConcurrentHashMap<>();

    public RateLimiter getRateLimiter(final String operationName, final String apiKey) {
        String rateLimiterName = getRateLimiterName(operationName, apiKey);
        return Optional.ofNullable(rateLimiters.getOrDefault(rateLimiterName, null))
            .orElseGet(() -> {
                RateLimiter rateLimiter = RateLimiter.of(rateLimiterName, rateLimiterRegistry.getConfiguration(operationName).orElseThrow());
                rateLimiters.putIfAbsent(rateLimiterName, rateLimiter);
                return rateLimiter;
            });
    }

    @Scheduled(fixedDelay = 5, timeUnit = MINUTES)
    private void cleanupRateLimiters() {
        rateLimiters.entrySet()
            .removeIf(e -> {
                RateLimiter rateLimiter = e.getValue();
                int availablePermissions = rateLimiter.getMetrics().getAvailablePermissions();
                int limitForPeriod = rateLimiter.getRateLimiterConfig().getLimitForPeriod();
                return availablePermissions >= limitForPeriod;
            });
    }

    private String getRateLimiterName(String operationName, String apiKey) {
        return StringUtils.joinWith("-", operationName, apiKey);
    }
}
