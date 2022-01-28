package com.gzhu.config;

import com.gzhu.pojo.rabbitmq.RabbitmqProperties;
import com.gzhu.pojo.rabbitmq.RabbitmqSendData;
import com.gzhu.pojo.rabbitmq.RabbitmqToolName;
import com.gzhu.util.redis.RedisUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class RabbitmqConfig {
    @Autowired
    private RedisUtils redisUtils;

    @Value("${rabbitmq.publish.retryCount}")
    private int RETRY_COUNT;

    //这里不用多实例会不会出现问题  channel是单线程的??
    @Bean
    @Scope("prototype")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setMessageConverter(new SerializerMessageConverter());

        template.setReturnCallback((message, replyCode1, replyText1, exchange1, routingKey1)->{
            System.out.println("发送失败："+message+"---" + exchange1 + "---" + routingKey1);
            //可能由于路由等原因发送失败 重新发送
            CorrelationData correlationData = new CorrelationData();
            String uuid = UUID.randomUUID().toString().replace("-","");
            correlationData.setId(uuid);
            template.convertAndSend(exchange1,routingKey1,new String(message.getBody()),correlationData);
        });
        template.setConfirmCallback((correlationData1, ack, cause)->{
            String id = correlationData1.getId();
            if(ack)
            {
                System.out.println("发送成功!" + id);
                redisUtils.del("rabbitmq:message:" + id);
                redisUtils.setRemove("rabbitmq:message:msgIds",id);
            }else{
                System.out.println("发送失败:" + correlationData1.getId() + "失败原因:" + cause);
                RabbitmqSendData rabbitmqSendData = (RabbitmqSendData) redisUtils.get("rabbitmq:message:" + id);
                int retryCount = rabbitmqSendData.getRetryCount();
                if(RETRY_COUNT>=retryCount){
                    //如果重试次数小于设定 继续重试
                    rabbitmqSendData.setRetryCount(++retryCount);
                    redisUtils.set("rabbitmq:message:" + id,rabbitmqSendData);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    template.convertAndSend(rabbitmqSendData.getExchangeName(),rabbitmqSendData.getRoutingKey(),rabbitmqSendData.getMessageData(),
                            correlationData1);
                }else{
                    //重试次数大于设定 将消息放到数据库 记录日志
                    System.out.println("发送消息失败:" + rabbitmqSendData);
                    redisUtils.del("rabbitmq:message:" + id);
                    redisUtils.setRemove("rabbitmq:message:msgIds",id);
                }
            }
        });
        return template;
    }

    @Value("${order.ttl.time}")
    private long ORDER_TTL;

    @Bean(RabbitmqToolName.EXPIRE_ORDER_EXCHANGE)
    public Exchange exchange(){
        return ExchangeBuilder.directExchange(RabbitmqToolName.EXPIRE_ORDER_EXCHANGE).durable(false).build();
    }

    @Bean(RabbitmqToolName.EXPIRE_ORDER_QUEUE)
    public Queue queue(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", RabbitmqToolName.HANDLER_ORDER_EXCHANGE);
        map.put("x-dead-letter-routing-key", RabbitmqToolName.HANDLER_ORDER_KEY);
        map.put("x-message-ttl",ORDER_TTL);
        map.put("x-max-length",RabbitmqToolName.EXPIRE_ORDER_QUEUE_LENGTH);
        map.put("x-max-priority",RabbitmqToolName.EXPIRE_ORDER_QUEUE_PRIORITY);
        map.put("x-overflow", RabbitmqProperties.REJECT_PUBLISH);
        return QueueBuilder.nonDurable(RabbitmqToolName.EXPIRE_ORDER_QUEUE).withArguments(map).build();
    }

    @Bean
    public Binding binding(@Qualifier(RabbitmqToolName.EXPIRE_ORDER_QUEUE)Queue queue,@Qualifier(RabbitmqToolName.EXPIRE_ORDER_EXCHANGE)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(RabbitmqToolName.EXPIRE_ORDER_KEY).noargs();
    }

    @Bean(RabbitmqToolName.HANDLER_ORDER_QUEUE)
    public Queue queue1(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-max-length",RabbitmqToolName.HANDLER_ORDER_QUEUE_LENGTH);
        map.put("x-max-priority",RabbitmqToolName.HANDLER_ORDER_QUEUE_PRIORITY);
        map.put("x-overflow", RabbitmqProperties.REJECT_PUBLISH);
        return QueueBuilder.nonDurable(RabbitmqToolName.HANDLER_ORDER_QUEUE).withArguments(map).build();
    }

    @Bean(RabbitmqToolName.HANDLER_ORDER_EXCHANGE)
    public Exchange exchange1(){
        return ExchangeBuilder.directExchange(RabbitmqToolName.HANDLER_ORDER_EXCHANGE).durable(false).build();
    }

    @Bean
    public Binding binding1(@Qualifier(RabbitmqToolName.HANDLER_ORDER_QUEUE)Queue queue,@Qualifier(RabbitmqToolName.HANDLER_ORDER_EXCHANGE)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(RabbitmqToolName.HANDLER_ORDER_KEY).noargs();
    }

    @Bean(RabbitmqToolName.CREATE_ORDER_QUEUE)
    public Queue queue2(){
        Map<String,Object> map = new HashMap<>();
        map.put("x-max-length",RabbitmqToolName.CREATE_ORDER_QUEUE_LENGTH);
        map.put("x-max-priority",RabbitmqToolName.CREATE_ORDER_QUEUE_PRIORITY);
        map.put("x-overflow", RabbitmqProperties.REJECT_PUBLISH);
        return QueueBuilder.nonDurable(RabbitmqToolName.CREATE_ORDER_QUEUE).withArguments(map).build();
    }

    @Bean(RabbitmqToolName.CREATE_ORDER_EXCHANGE)
    public Exchange exchange2(){
        return ExchangeBuilder.directExchange(RabbitmqToolName.CREATE_ORDER_EXCHANGE).durable(false).build();
    }

    @Bean
    public Binding binding2(@Qualifier(RabbitmqToolName.CREATE_ORDER_QUEUE)Queue queue,@Qualifier(RabbitmqToolName.CREATE_ORDER_EXCHANGE)Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(RabbitmqToolName.CREATE_ORDER_KEY).noargs();
    }
}
