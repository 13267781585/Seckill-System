package com.gzhu.pojo.rabbitmq;

public class RabbitmqToolName {
    //订单倒计时交换机和队列
    public static final String EXPIRE_ORDER_QUEUE = "expire_order_queue";
    public static final String EXPIRE_ORDER_EXCHANGE = "expire_order_exchange";
    public static final String EXPIRE_ORDER_KEY = "expire_order_key";
    public static final long EXPIRE_ORDER_QUEUE_LENGTH = 100;
    public static final long EXPIRE_ORDER_QUEUE_PRIORITY = 10;

    //订单倒计时队列 TO 订单到期交换机 key
    public static final String EXPIRE_TO_HANDLER_ORDER_KEY = "expire_to_handler_order_key";

    //订单到期处理
    public static final String HANDLER_ORDER_QUEUE = "handler_order_queue";
    public static final String HANDLER_ORDER_EXCHANGE = "handler_order_exchange";
    public static final String HANDLER_ORDER_KEY = "handler_order_key";
    public static final long HANDLER_ORDER_QUEUE_LENGTH = 100;
    public static final long HANDLER_ORDER_QUEUE_PRIORITY = 8;

    //订单生成处理
    public static final String CREATE_ORDER_QUEUE = "create_order_queue";
    public static final String CREATE_ORDER_EXCHANGE = "create_order_exchange";
    public static final String CREATE_ORDER_KEY = "create_order_key";
    public static final long CREATE_ORDER_QUEUE_LENGTH = 100;
    public static final long CREATE_ORDER_QUEUE_PRIORITY = 10;
}
