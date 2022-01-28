package test.lock;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3);
        ExecutorService service = Executors.newFixedThreadPool(6);
        for(int i=0;i<3;i++)
        {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getId() + " start ...");
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getId() + " run...");
                }
            });
        }
    }
}
