package com.gzhu.interceptor;

import com.gzhu.interceptor.interfaces.ReCommitDefense;
import com.gzhu.pojo.user.User;
import com.gzhu.util.EncryptUtils;
import com.gzhu.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Objects;

@Component
public class ReCommitInterceptor implements HandlerInterceptor {
    @Value("${reCommit.enable}")
    private String RECOMMIT_ENABLE;

    private static final String RECOMMIT_ENABLE_OPEN = "1";
    private static final String REQUEST_GET = "GET";
    private static final String REQUEST_POST = "POST";

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 防止重复提交 用在会产生财产安全的请求上 前提是用户已经登录
     * 不需要登录的提交一半防止重复提交并没有意义
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!RECOMMIT_ENABLE_OPEN.equals(RECOMMIT_ENABLE))
        {
            return true;
        }
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            //查询是否添加注解
            ReCommitDefense annotation = method.getAnnotation(ReCommitDefense.class);
            if(!Objects.isNull(annotation))
            {
                final String REQUEST_METHOD = request.getMethod();
                final long expire = annotation.expireTime();
                String dataDigest = "";

                //先获取用户的信息
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                if(Objects.isNull(user))
                {
                    throw new Exception("用户未登录！");
                }
                Long userId = user.getId();

                if(REQUEST_GET.equals(REQUEST_METHOD))
                {
                    String queryStr = request.getQueryString();
                    dataDigest = EncryptUtils.MD5(queryStr);
                }else if(REQUEST_POST.equals(REQUEST_METHOD))
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader( (ServletInputStream) request.getInputStream(), "utf-8"));
                    StringBuffer sb = new StringBuffer();
                    String temp;
                    while ((temp = br.readLine()) != null) {
                        sb.append(temp);
                    }
                    br.close();
                    dataDigest = EncryptUtils.MD5(sb.toString());
                }
                String key = "recommit:" + userId + ":" + request.getServletPath().replace("/","") + ":" + dataDigest;
                if(redisUtils.hasKey(key))
                {
                    throw new Exception("重复提交，请稍后再试!");
                }
                redisUtils.set(key,null,expire);
            }

        }

        return true;
    }
}
