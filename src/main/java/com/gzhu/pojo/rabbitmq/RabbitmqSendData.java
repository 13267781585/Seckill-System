package com.gzhu.pojo.rabbitmq;

import java.util.UUID;

public class RabbitmqSendData {
    private String id;
    private String exchangeName;
    private String routingKey;
    private Object messageData;
    private int retryCount;

    public RabbitmqSendData() {
    }

    public RabbitmqSendData(String id, String exchangeName, String routingKey, Object messageData, int retryCount) {
        this.id = id;
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.messageData = messageData;
        this.retryCount = retryCount;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public Object getMessageData() {
        return messageData;
    }

    public void setMessageData(Object messageData) {
        this.messageData = messageData;
    }

    public RabbitmqSendData(String exchangeName, String routingKey, Object messageData) {
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.messageData = messageData;
    }

    public static RabbitmqSendData builder(String exchangeName, String routingKey, Object object){
        RabbitmqSendData data = new RabbitmqSendData(exchangeName,routingKey,object);
        data.setRetryCount(0);
        data.setId(UUID.randomUUID().toString().replace("-",""));
        return data;
    }

    @Override
    public String toString() {
        return "RabbitmqSendData{" +
                "id='" + id + '\'' +
                ", exchangeName='" + exchangeName + '\'' +
                ", routingKey='" + routingKey + '\'' +
                ", messageData=" + messageData +
                ", retryCount=" + retryCount +
                '}';
    }
}
