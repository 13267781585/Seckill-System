package com.gzhu;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class PrintNum {
    public static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static CyclicBarrier barrier = new CyclicBarrier(2);
    public static CyclicBarrier barrier1 = new CyclicBarrier(2);
    public static Semaphore semaphore = new Semaphore(1);
    public static Semaphore semaphore1 = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(1);
            try {
                semaphore.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread thread1 = new Thread(() -> {
            try {
                semaphore.acquire();
                System.out.println(2);
                semaphore1.release();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        Thread thread2 = new Thread(() -> {
            try {
                semaphore1.acquire();
                semaphore1.release();
                System.out.println(3);
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        thread2.start();
        thread1.start();
        thread.start();
        thread2.join();
        thread.join();
        thread1.join();
    }
}
