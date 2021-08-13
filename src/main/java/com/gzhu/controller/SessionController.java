package com.gzhu.controller;

import com.gzhu.pojo.user.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;

@RestController
public class SessionController {
    @RequestMapping("/setSession")
    public String setSession(HttpServletRequest request){
        User user = new User();
        user.setId(111L);
        user.setName("李四" + System.currentTimeMillis());
        user.setPassword("243432432");
        request.getSession().setAttribute("user",user);
        return "success";
    }

    @RequestMapping("/setSession1")
    public String setSession1(HttpServletRequest request) throws InterruptedException {
        request.getSession().setAttribute("immediate","111");
        Thread.sleep(10000);
        return "immediate success";
    }

    @RequestMapping("/getSession1")
    public String getSession1(HttpServletRequest request) throws InterruptedException {
        Thread.sleep(10000);
        return (String) request.getSession().getAttribute("immediate") == null ? "null" : (String) request.getSession().getAttribute("immediate");
    }

    @RequestMapping("/getSession")
    public User getSession(HttpServletRequest request)
    {
        return (User) request.getSession().getAttribute("user");
    }

    @RequestMapping("/invalidateSession")
    public void invalidateSession(HttpServletRequest request)
    {
        request.getSession().invalidate();
    }

    @RequestMapping("/flush")
    public void flush(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = new User();
        user.setId(111L);
        user.setName("李四");
        user.setPassword("243432432");
        request.getSession().setAttribute("user",user);
        PrintWriter printWriter = response.getWriter();
        for(int i=0;i<5;i++){
            printWriter.println(i);
            response.flushBuffer();
            System.out.println(response.isCommitted());
            Thread.sleep(10000);
            printWriter.close();
        }

    }

    public static void main(String[] args) {
        int[] a = {1,2,3};
        System.out.println(Arrays.toString(Arrays.copyOfRange(a,0,1)));
    }

}
