package com.gzhu.controller;

import com.gzhu.pojo.order.Order;
import com.gzhu.pojo.rabbitmq.RabbitmqSendData;
import com.gzhu.pojo.rabbitmq.RabbitmqToolName;
import com.gzhu.pojo.user.User;
import com.gzhu.service.SecKillService;
import com.gzhu.util.RabbitmqUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@RestController
public class SecKillController {
    @Autowired
    private SecKillService secKillService;

    private String DEFAULT_OPEN = "1";

    @Value("${seckill.userAuth}")
    private String SECKILL_USER_AUTH;

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

}
