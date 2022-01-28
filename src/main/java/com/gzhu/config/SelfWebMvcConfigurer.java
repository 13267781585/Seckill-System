package com.gzhu.config;

import com.gzhu.interceptor.CrsfAttackInterceptor;
import com.gzhu.interceptor.RateLimiterInterceptor;
import com.gzhu.interceptor.ReCommitInterceptor;
import com.gzhu.interceptor.ReplayAttackInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SelfWebMvcConfigurer implements WebMvcConfigurer {
    @Autowired
    private ReplayAttackInterceptor replayAttackInterceptor;
    @Autowired
    private ReCommitInterceptor reCommitInterceptor;
    @Autowired
    private CrsfAttackInterceptor crsfAttackInterceptor;
    @Autowired
    private RateLimiterInterceptor rateLimiterInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //重放攻击拦截器
        InterceptorRegistration registration = registry.addInterceptor(replayAttackInterceptor);
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/login",            //登录
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css"             //css静态资源
        );

        //重放攻击拦截器
        InterceptorRegistration registration1 = registry.addInterceptor(reCommitInterceptor);
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/login",            //登录
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css"             //css静态资源
        );

        //crsf攻击拦截器
        //重放攻击拦截器
        InterceptorRegistration registration2 = registry.addInterceptor(crsfAttackInterceptor);
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/login",            //登录
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css"             //css静态资源
        );

        //crsf攻击拦截器
        //重放攻击拦截器
        InterceptorRegistration registration3 = registry.addInterceptor(rateLimiterInterceptor);
        registration.addPathPatterns("/**");                      //所有路径都被拦截
        registration.excludePathPatterns(                         //添加不拦截路径
                "/login",            //登录
                "/**/*.html",            //html静态资源
                "/**/*.js",              //js静态资源
                "/**/*.css"             //css静态资源
        );
    }
}
