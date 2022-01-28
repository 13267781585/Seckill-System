package com.gzhu.util;

import com.alibaba.fastjson.JSONObject;
import com.gzhu.pojo.rabbitmq.RabbitmqSendData;
import com.gzhu.util.redis.RedisUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqUtils {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisUtils redisUtils;

    public void sendMessageByConfirm(RabbitmqSendData data){
        handlerMessage(data);

        String exchangeName = data.getExchangeName();
        String routingKey = data.getRoutingKey();
        Object messageData = data.getMessageData();

        CorrelationData correlationData = new CorrelationData();
        String uuid = data.getId();
        correlationData.setId(uuid);

        rabbitTemplate.convertAndSend(exchangeName,routingKey,messageData,correlationData);
    }

    //将消息放入redis
    private void handlerMessage(RabbitmqSendData rabbitmqSendData)
    {
        String msgId = rabbitmqSendData.getId();
        redisUtils.set("rabbitmq:message:" + msgId, rabbitmqSendData);
        redisUtils.sSet("rabbitmq:message:msgIds",msgId);
    }

}
