package com.gzhu.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gzhu.mapper.OrderMapper;
import com.gzhu.pojo.order.Order;
import com.gzhu.pojo.order.OrderStatus;
import com.gzhu.pojo.rabbitmq.RabbitmqSendData;
import com.gzhu.pojo.rabbitmq.RabbitmqToolName;
import com.gzhu.util.RabbitmqUtils;
import com.gzhu.util.redis.RedisUtils;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class RabbitmqService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RabbitmqUtils rabbitmqUtils;

    @RabbitListener(queues = RabbitmqToolName.HANDLER_ORDER_QUEUE)
    public void expireOrderListener(Message message, Channel channel) throws Exception {
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        Order order = JSON.parseObject(body, Order.class);
        order.setStatus(OrderStatus.ORDER_UNPAID);
        System.out.println("收到过期订单消息:"+ order);

        String uuid = order.getOrderUuid();
        Integer userId = order.getUserId();
        //查询是否已经消费
        if(redisUtils.sHasKey("seckill:expireOrders:consumerOrders",uuid)){
            System.out.println("订单已经处理过，不需要再重复处理:" + order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return;
        }

        //判断是否已经付款
        Order orderRedis = (Order) redisUtils.get("seckill:orders:" + userId + ":" + uuid);
        if(Objects.nonNull(orderRedis)&&OrderStatus.ORDER_PAID.equals(orderRedis.getStatus())){
            System.out.println("订单已经付过款，不需要再重复处理:" + order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return;
        }

//        List<Order> dataOrder = orderMapper.selectByUserIdAndUUid(userId,uuid);
//        if(!CollectionUtils.isEmpty(dataOrder))
//        {
//            System.out.println("订单已经处理过，不需要再重复处理:" + uuid);
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//            return;
//        }

        orderMapper.updateByPrimaryKey(order);
        redisUtils.sSet("seckill:expireOrders:consumerOrders",uuid);
        redisUtils.set("seckill:orders:" + userId + ":" + uuid,order);
        System.out.println("过期订单处理完成:" + order);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = RabbitmqToolName.CREATE_ORDER_QUEUE)
    public void createOrderListener(Message message, Channel channel) throws Exception{
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        Order order = JSON.parseObject(body, Order.class);
        String uuid = order.getOrderUuid();
        Integer userId = order.getUserId();
        System.out.println("收到创建订单消息:"+ order);

        //判断是否已经创建
        if(redisUtils.hasKey("seckill:orders:" + userId + ":" + uuid))
        {
            System.out.println("订单已经创建:" + order);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }
        //创建订单
        order.setStatus(OrderStatus.ORDER_CREATE);
        int count = orderMapper.insertSelective(order);
        if(count != 1)
        {
            System.out.println("订单生成错误:" + order);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
        //将订单放入redis
        redisUtils.set("seckill:orders:" + userId + ":" + uuid,order);
        System.out.println("订单创建完成:" + order);

        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        rabbitmqUtils.sendMessageByConfirm(RabbitmqSendData.builder(RabbitmqToolName.EXPIRE_ORDER_EXCHANGE,RabbitmqToolName.EXPIRE_ORDER_KEY,
                JSONObject.toJSONString(order)));
    }
}
