package com.gzhu.controller;

import com.gzhu.common.ServerReturnDTO;
import com.gzhu.interceptor.interfaces.ReCommitDefense;
import com.gzhu.interceptor.interfaces.ReplayAttackDefense;
import com.gzhu.pojo.user.User;
import com.gzhu.service.UserService;
import com.gzhu.util.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

@RestController
public class UserController {
    @Value("${user.loginTokenExpire}")
    private int USER_TOKEN_EXPIRE_TIME;

    @Autowired
    private UserService userService;

    @ReplayAttackDefense
    @ReCommitDefense
    @RequestMapping("/register")
    public ServerReturnDTO userRegister(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
        String name = user.getName();
        String password = user.getPassword();
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);
        //先模拟前端加密密码
        user.setPassword(EncryptUtils.MD5(password));

        //注册
        user = userService.userRegister(user);
        User userDto = builderReturnUser(user);
        ServerReturnDTO returnDTO = ServerReturnDTO.createBySuccess();
        returnDTO.setUser(userDto);
        return returnDTO;
    }

    @RequestMapping("/loginToken")
    public ServerReturnDTO loginToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String loginToken = getLoginToken(request);
        if(Objects.isNull(loginToken))
        {
            throw new Exception("token信息错误！");
        }
        User user = userService.tokenLogin(loginToken);
        String crsfToken = UUID.randomUUID().toString().replace("-","");
        operationAfterLogin(request,response,user,crsfToken);
        User userDto = builderReturnUser(user);
        ServerReturnDTO returnDTO = ServerReturnDTO.createBySuccess();
        returnDTO.setUser(userDto);
        returnDTO.setCrsfToken(crsfToken);
        return returnDTO;
    }

    private String getLoginToken(HttpServletRequest request) {
        //先从cookie中查询  没有在查找header
        Cookie[] cookies = request.getCookies();
        for(Cookie temp : cookies){
            if("login_token".equals(temp.getName()))
            {
                return temp.getValue();
            }
        }

        return request.getHeader("login_token");
    }

    @RequestMapping("/login")
    public ServerReturnDTO userLogin(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
        String name = user.getName();
        String password = user.getPassword();
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);
        //先模拟前端加密密码
        user.setPassword(EncryptUtils.MD5(password));
        user = userService.userLogin(user);

        User userDto = builderReturnUser(user);
        String crsfToken = UUID.randomUUID().toString().replace("-","");
        operationAfterLogin(request,response,user,crsfToken);

        ServerReturnDTO returnDTO = ServerReturnDTO.createBySuccess();
        returnDTO.setUser(userDto);
        returnDTO.setCrsfToken(crsfToken);
        return returnDTO;
    }

    private User builderReturnUser(User user) {
        User temp = new User();
        temp.setId(user.getId());
        temp.setName(user.getName());
        return temp;
    }

    /**
     * 将用户信息存放到session->redis
     * 将token和session放入头部和cookie
     * @param request
     * @param response
     * @param user
     */
    private void operationAfterLogin(HttpServletRequest request, HttpServletResponse response, User user,String crsfToken)
    {
        HttpSession session = request.getSession();
        session.setAttribute("user",user);
        //在session中放入crsfToken 防御crsf攻击
        session.setAttribute("crsfToken",crsfToken);

        //spring-session会将sessionid放入Cookie
        response.setHeader("session_id",session.getId());
        response.setHeader("login_token",user.getToken());
        Cookie cookie = new Cookie("login_token",user.getToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge(USER_TOKEN_EXPIRE_TIME);
        response.addCookie(cookie);
    }
}
