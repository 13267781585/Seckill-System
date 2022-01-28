package test.limiter;

import com.google.common.util.concurrent.RateLimiter;

public class Main {
    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(5);
        //返回需要等待的时间
        //若因为前一个请求的预支配或者开启了预热限流，则会阻塞至拿到令牌在返回
        double waitTime = rateLimiter.acquire(2);
        //判断是否可以拿到令牌，不行直接返回是，不会进行阻塞
            boolean isGet = rateLimiter.tryAcquire(2);
    }
}
