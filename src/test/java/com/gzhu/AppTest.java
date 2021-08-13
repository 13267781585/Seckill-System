package com.gzhu;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.gzhu.pojo.order.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Unit test for simple App.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = SecKillApplication.class)
public class AppTest 
{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    public void testRedis(){
        System.out.println(redisTemplate.getExpire("li"));
    }

    public static void main(String[] args) throws Exception {
//        Order order = new Order();
//        order.setUserId(1);
//        //order.setGoodId(2);
//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("D:\\object.txt"));
//        objectOutputStream.writeObject(order);
//        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("D:\\object.txt"));
        Order order1 = (Order) objectInputStream.readObject();
        System.out.println(order1);
    }

}
