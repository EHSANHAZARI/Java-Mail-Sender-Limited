		package com.example.JavaMailSender.interceptor;
		import java.util.concurrent.TimeUnit;

		import org.slf4j.Logger;
		import org.slf4j.LoggerFactory;
		import org.springframework.http.HttpStatus;
		import org.springframework.web.servlet.HandlerInterceptor;
		import io.github.bucket4j.Bucket;
		import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

		public class RateLimitInterceptor implements HandlerInterceptor {

		    private static final Logger logger = LoggerFactory.getLogger(RateLimitInterceptor.class);

		    private final Bucket bucket;
		    private final int numOfTokens;

		    public RateLimitInterceptor(Bucket bucket, int numOfTokens) {
		        this.bucket = bucket;
		        this.numOfTokens = numOfTokens;
		    }

		    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		    	// ConsumptionProbe represents the tokens used for the current request
		        ConsumptionProbe consumptionProbe = this.bucket.tryConsumeAndReturnRemaining(this.numOfTokens);
		        //If the token is consumed ...
		        if (consumptionProbe.isConsumed()) {
		            response.addHeader("X-Remaining-Tokens", Long.toString(consumptionProbe.getRemainingTokens()));
		            logger.debug("Request allowed. Remaining tokens: {}", consumptionProbe.getRemainingTokens());
		            return true;
		        } else {
		            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
		            response.addHeader("X-Rate-Limit-Retry-After-Milliseconds",
		                    Long.toString(TimeUnit.NANOSECONDS.toMillis(consumptionProbe.getNanosToWaitForRefill())));
		            logger.debug("Request blocked due to rate limit. Retry after: {} ms",
		                    TimeUnit.NANOSECONDS.toMillis(consumptionProbe.getNanosToWaitForRefill()));
		            return false;
		        }
		    }
		}
