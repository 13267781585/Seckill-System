package com.gzhu.service;

import com.alibaba.fastjson.JSON;
import com.gzhu.pojo.order.Order;
import com.gzhu.pojo.rabbitmq.RabbitmqToolName;
import com.gzhu.util.RedisUtils;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class RabbitmqService {
    @Autowired
    private RedisUtils redisUtils;

    @RabbitListener(queues = RabbitmqToolName.HANDLER_ORDER_QUEUE)
    public void listener(Message message, Channel channel) throws Exception {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        Order order = JSON.parseObject(body, Order.class);
        System.out.println("收到消息:"+ order);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
