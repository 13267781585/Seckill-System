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

    /**
     *      库存  redis mysql
     *      订单 mysql
     *      交易表 放在mysql
     *      1. 需要保证redis和mysql库存数据一致性，在异步下单后新增订单和减库存
     *                                        在订单规定时间内没有支付时，修改订单状态和加库存
     *
     *      a.抢购执行流程：
     *      1. 查库存 redis
     *      2. 获取redis分布式锁
     *      3. 在查一次库存
     *      4. 生成订单信息
     *      5. 记录交易表 交易单 状态 准备  ######
     *      6. 减库存 redis
     *      7. 记录 交易表 状态 减redis库存  ########
     *      8. 释放redis分布式锁
     *      9. 消息队列异步下单
     *
     *      b.消息队列异步下单流程
     *      1. 开启事务
     *      2. 订单落库
     *      3. 减库存 mysql
     *      4. 改交易单状态为 已下单    ########
     *      5. 提交事务
     *      6. 发送消息延时支付
     *
     *      c.延时支付流程(规定时间后消费)
     *      1. 检查数据库该订单状态  未支付->订单失效+加库存(redis+mysql)
     *                           已支付->不处理
     *      2. 修改交易单状态                         ############
     *
     *       d.用户支付流程
     *       1. 检查对应订单是否状态未支付 是->支付成功
     *                                否->支付失败
     *       2. 修改交易单 交易状态                   ###############
     *
     *       e. 订单过期处理流程
     *       1. 开事务
     *       2. 修改订单状态
     *       3. 加库存
     *       4. 修改交易状态 加mysql库存         ###############
     *       5. 提交事务
     *       6. 加 redis 库存
     *       7. 修改交易状态 已失效                 ##########
     *
     *
     *       f. 后台定时任务的流程
     *          ## 交易单可能存在的状态
     *          1. 准备 -> 是否减了redis 的库存？
     *                  i. 获取redis分布式锁
     *                  ii. 统计 除了 减库存、加mysql库存、已失效 除外状态的订单数，用秒杀前商品数量-统计的数量得出现在的库存
     *                  iii. 对于  准备的交易单，不确定是否减了redis库存，恢复后不影响，直接将交易状态改为失效
     *                             加mysql库存状态的交易单 因为mysql库存已经加上，redis中库存恢复后的库存也是加上这个交易单的，直接将改交易单状态改为 已失效
     *                  iv . 释放分布式锁
     *
     *          4. 加mysql库存 -> redis库存恢复了但是改交易单的操作失败了  或者 redis库存没有恢复
     *                  i. 获取redis分布式锁 -> 获取到分布式锁后，交易单的状态只有可能是准备后的状态 交易单为准备说明是宕机的服务器处理的订单
     *                  ii. 统计 除了失效的订单数量，用 商品秒杀前的数量 - 卖出去的数量 还原 现在库存
     *                  iii. 释放分布式锁
     *
     *          2. 减redis库存 -> 异步下单操作发送失败 或者是  下单已经成功但是修改交易表的操作失败
     *                  查看数据库是否存在订单  有->改交易单状态
     *                                      没有->发送消息消息队列下订单，改交易单状态
     *
     *          3. 已下单 -> 判断是否到达订单失效的时间，还没到->不处理
     *                                             到了但是订单还是已下单状态，说明延时消费出现问题 ->
     *                  还没过期 -> 不处理
     *                  过期 -> 订单失效处理
     *
     *
     *          5. 已支付
     *              不处理
     *          6. 已失效
     *              不处理
     *
     *       g. 交易表字段
     *          1. 订单oid
     *          2. 订单详细信息(json) -> 不必要订单信息展开，序列化为json，需要时做反序列化即可
     *          3. 交易单创建时间
     *          4. 交易单上次更改时间
     *          5. 交易单失效时间
     *          6. 交易单状态
     *
     */
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
