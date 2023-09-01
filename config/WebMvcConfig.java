package com.example.JavaMailSender.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import com.example.JavaMailSender.interceptor.RateLimitInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {    
    // Create a shared Bucket instance for rate limiting all users
    private static final Bucket sharedBucket = createSharedBucket();

    private static Bucket createSharedBucket() {
    	// Two ways of refilling "greedy" and "classic" 
        Refill refill = Refill.greedy(2, Duration.ofDays(1));
        // Creating Limit the bucket limit and providing initial tokens
        Bandwidth limit = Bandwidth.classic(2, refill).withInitialTokens(3);
        // Build the Bucket with the appropriate limit
        return Bucket.builder().addLimit(limit).build();
    }

    public void addInterceptors(InterceptorRegistry registry) {
    	// Add the intercepter to registry API.
        registry.addInterceptor(new RateLimitInterceptor(sharedBucket, 2))
            .addPathPatterns("/email");
    }
}
