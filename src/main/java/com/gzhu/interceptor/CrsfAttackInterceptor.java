package com.gzhu.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Component
public class CrsfAttackInterceptor implements HandlerInterceptor {
    @Value("${crsf.enable}")
    private String CRSF_ENABLE;

    private static final String CRSF_ENABLE_OPEN = "1";
    private static final String REQUEST_METHOD_POST = "POST";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!CRSF_ENABLE_OPEN.equals(CRSF_ENABLE))
        {
            return true;
        }

        final String METHOD = request.getMethod();
        if(REQUEST_METHOD_POST.equalsIgnoreCase(METHOD))
        {
            String verfCrsfToken = request.getHeader("crsfToken");
            if(Objects.isNull(verfCrsfToken))
            {
                throw new Exception("crsf攻击!");
            }
            HttpSession session = request.getSession();
            String crsfToken = (String) session.getAttribute("crsfToken");
            if(Objects.isNull(crsfToken))
            {
                throw new Exception("没有csrfToken,请登录后再操作!");
            }
            if(!crsfToken.equals(verfCrsfToken))
            {
                throw new Exception("csrfToken错误,请重新登录!");
            }
        }

        return true;
    }
}
