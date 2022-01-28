package com.gzhu;

import java.util.concurrent.FutureTask;

public class ThreadTest {
    public final Object o = new Object();
    public volatile int count=0;

    public void consume() throws InterruptedException {
        while(true)
        {
            synchronized (o)
            {
                if(count>0){
                    count-=5;
                    System.out.println("消费:" + count);
                    o.wait();
                }
            }
        }
    }

    public void produce(){
        while(true){
            synchronized (o)
            {
                if(count<=100){
                    count++;
                    System.out.println("生产:" + count);
                    o.notify();
                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadTest a = new ThreadTest();
        new Thread(()->{
            try {
                a.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            try {
                a.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(()->{
            a.produce();
        }).start();
        for(;;);
    }
}
