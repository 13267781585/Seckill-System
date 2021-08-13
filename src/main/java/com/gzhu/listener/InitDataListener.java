package com.gzhu.listener;

import com.gzhu.mapper.GoodsMapper;
import com.gzhu.pojo.Goods;
import com.gzhu.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitDataListener implements ApplicationRunner {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private GoodsMapper goodsMapper;

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
    }
}
