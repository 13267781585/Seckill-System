package com.gzhu.controller;

import com.gzhu.interceptor.interfaces.RateLimiter;
import com.gzhu.pojo.order.Order;
import com.gzhu.pojo.rabbitmq.RabbitmqSendData;
import com.gzhu.pojo.rabbitmq.RabbitmqToolName;
import com.gzhu.pojo.user.User;
import com.gzhu.service.SecKillService;
import com.gzhu.util.RabbitmqUtils;
import com.gzhu.util.redis.RedisUtils;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

@RestController
public class SecKillController {
    @Autowired
    private SecKillService secKillService;

    private String DEFAULT_OPEN = "1";

    @Value("${seckill.userAuth}")
    private String SECKILL_USER_AUTH;

    @RateLimiter(apiName = "seckill")
    @RequestMapping("/seckill")
    public void seckill(HttpServletRequest request, Order order) throws Exception {
        if(DEFAULT_OPEN.equals(SECKILL_USER_AUTH)){
            Integer userId = order.getUserId();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if(Objects.isNull(userId) || Objects.isNull(user) || !userId.equals(user.getId())){
                throw new Exception("请登录后再试!");
            }
        }
        secKillService.secKill(order);
    }

    @Autowired
    private RabbitmqUtils rabbitmqUtils;
    @RequestMapping("/mq")
    public void mqTest(){
        for(int i=0;i<10;i++){
            rabbitmqUtils.sendMessageByConfirm(RabbitmqSendData.builder(RabbitmqToolName.EXPIRE_ORDER_EXCHANGE,RabbitmqToolName.EXPIRE_ORDER_KEY,String.valueOf(i)));
        }
    }

    @Autowired
    private RedisUtils redisUtils;
    @RequestMapping("/redis")
    public void mqTest1(){
        redisUtils.set(UUID.randomUUID().toString(),1);
    }

    @RequestMapping("/session")
    public void mqTest2(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(request.getRequestedSessionId());
        System.out.println(session.getId());
        session.setAttribute("id","233434");
        session.invalidate();
        System.out.println(request.getRequestedSessionId());
        HttpSession session1 = request.getSession();
        System.out.println(session1.getId());
    }

}
