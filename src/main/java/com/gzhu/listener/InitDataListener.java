package com.gzhu.listener;

import com.gzhu.mapper.GoodsMapper;
import com.gzhu.pojo.Goods;
import com.gzhu.util.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitDataListener implements ApplicationRunner {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private GoodsMapper goodsMapper;
    @Value("${isRateLimiter}")
    private String isRateLimiter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化redis的数据
        List<Goods> goodsList = goodsMapper.select();
        for(Goods goods:goodsList)
        {
            redisUtils.setIfAbsent("seckill:goods:"+goods.getId(),goods);
            //redisUtils.setIfAbsent("seckill:goodsCount:"+goods.getId(),goods.getStock());
            redisUtils.set("seckill:goodsCount:"+goods.getId(),goods.getStock());
        }
        redisUtils.del("seckill:data:count");
        redisUtils.del("seckill:order:consumerOrders");

        //初始化限流数据
        if("1".equals(isRateLimiter)){
            List<String> keyList = new ArrayList<>();
            keyList.add("{seckill}");
            redisUtils.eval("/lua/init.lua",keyList,Long.class,0,30,25,0);
        }
    }
}
