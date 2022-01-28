package com.gzhu.interceptor;

import com.gzhu.interceptor.interfaces.RateLimiter;
import com.gzhu.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

@Component
public class RateLimiterInterceptor implements HandlerInterceptor {
    @Value("${isRateLimiter}")
    private String isRateLimiter;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //记录处理的请求数目
        redisUtils.incr("seckill:data:RateCount",1);
        //判断是否需要限流
        if(isRateLimiter.equals("1")){
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                //查询是否添加注解
                RateLimiter annotation = method.getAnnotation(RateLimiter.class);
                //判断接口是否需要限流
                if (!Objects.isNull(annotation)) {
                    //限流的逻辑
                    String apiName = "{" + annotation.apiName() + "}";
                    Long nowTime = System.currentTimeMillis() / 1000;
                    Long isPass = (Long) redisUtils.eval("/lua/get.lua", Arrays.asList(apiName),Long.class,nowTime);
                    if(isPass == 1)
                    {
                        System.out.println("通过，时间:" + nowTime);
                        return true;
                    }else{
                        System.out.println("不通过，时间:" + nowTime);
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
