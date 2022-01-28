package com.gzhu.service;

import com.alibaba.fastjson.JSONObject;
import com.gzhu.mapper.GoodsMapper;
import com.gzhu.mapper.OrderMapper;
import com.gzhu.mapper.UserMapper;
import com.gzhu.pojo.Goods;
import com.gzhu.pojo.order.Order;
import com.gzhu.pojo.order.OrderStatus;
import com.gzhu.pojo.rabbitmq.RabbitmqSendData;
import com.gzhu.pojo.rabbitmq.RabbitmqToolName;
import com.gzhu.util.RabbitmqUtils;
import com.gzhu.util.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;


@Service
public class SecKillService {
    private static final Logger log = LoggerFactory.getLogger(SecKillService.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private RabbitmqUtils rabbitmqUtils;

    @Value("${sekill.getLock.count}")
    private int GET_LOCK_COUNT;

    public void secKill(Order order) throws Exception {
       Integer userId = order.getUserId();
       Integer goodId = order.getGoodId();

       //记录处理的请求数目
       redisUtils.incr("seckill:data:count",1);
        //获取商品详细信息
        Goods goods = null;
        try{
            goods = (Goods) redisUtils.get("seckill:goods:" + goodId);
        }catch (Exception e) {
            log.error(this.getClass().getName() + ":secKill" + e.getMessage());
        }finally {
            goods = goodsMapper.selectByPrimaryKey(goodId);
        }

        if(Objects.isNull(goods))
        {
            throw new Exception("秒杀商品不存在!");
        }

        //判断是否还有库存
        Integer goodsNum = (Integer) redisUtils.get("seckill:goodsCount:" + goodId);
        if(Objects.isNull(goodsNum) || goodsNum <= 0){
            System.out.println("活动结束！");
            return;
        }
        //获取分布式锁
        String key = "seckill:lock:goods" + goodId;
        String uuid = UUID.randomUUID().toString() + "_" + userId;
        int count = 0;
        while(!redisUtils.setIfAbsent(key,uuid)){
            if(++count>GET_LOCK_COUNT){
                System.out.println("抢购失败!");
                return;
            }
        }
        System.out.println("获取到抢购权限:   " + uuid);
        goodsNum = (Integer) redisUtils.get("seckill:goodsCount:" + goodId);
        if(Objects.isNull(goodsNum) || goodsNum <= 0){
            System.out.println("获取到抢购权限，但是库存为零，抢购结束！");
            releaseLock(goodId,uuid);
            return;
        }

        redisUtils.decr("seckill:goodsCount:" + goodId,1);
        releaseLock(goodId,uuid);
        System.out.println("抢购成功，当前库存:" + goodsNum);

        Order orderNew = new Order();
        orderNew.setUserId(userId);
        orderNew.setGoodId(goodId);
        orderNew.setStatus(OrderStatus.ORDER_UNPAID);
        orderNew.setOrderUuid(UUID.randomUUID().toString().replace("-",""));
        orderNew.setCreateTime(new Date());
        //orderMapper.insert(orderNew);
        //异步下单 固定事件支付

        rabbitmqUtils.sendMessageByConfirm(RabbitmqSendData.builder(RabbitmqToolName.CREATE_ORDER_EXCHANGE,RabbitmqToolName.CREATE_ORDER_KEY,
                JSONObject.toJSONString(orderNew)));
    }

    private void releaseLock(Integer goodId,String uuid)
    {
        String nowUuid = (String) redisUtils.get("seckill:lock:goods" + goodId);
        if(uuid.equals(nowUuid)){
            redisUtils.del("seckill:lock:goods" + goodId);
        }
    }

}
