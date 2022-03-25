package test.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintNum1 {
    private static final ReentrantLock lock = new ReentrantLock();
    private static volatile int num = 0;

    private static final Condition waitQueue = lock.newCondition();

    public static void main(String[] args) throws Exception{
        Thread thread = new Thread3();
        thread.start();
        Thread thread1 = new Thread4();
        thread1.start();
        Thread thread3 = new Thread5();
        thread3.start();
        thread.join();
    }

    public static class Thread5 extends Thread {
        public void run() {
            while(num<=100)
            {
                lock.lock();
                try{
                    if(num%2==1)
                    {
                        System.out.println("thread5 " + num++);
                    }
                    waitQueue.signalAll();
                    try {
                        waitQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }
            }

        }
    }

    public static class Thread4 extends Thread {
        public void run() {
            while(num<=100)
            {
                lock.lock();
                try{
                    if(num%2==1)
                    {
                        System.out.println("thread4 " + num++);
                    }
                    waitQueue.signalAll();
                    try {
                        waitQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }
            }

        }
    }
    public static class Thread3 extends Thread{
        public void run() {
            while(num<=100)
            {
                lock.lock();
                try{
                    if(num%2==0)
                    {
                        System.out.println("thread3 " + num++);
                    }
                    waitQueue.signalAll();
                    try {
                        waitQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    public static class Thread1 extends Thread{
        public void run(){
            while(num<=100)
            {
                lock.lock();
                try{
                    if(num%2==0){
                        System.out.println("thread1  " + num++);
                    }
                }finally {
                    lock.unlock();
                }
            }
        }
    }

    public static class Thread2 extends Thread{
        public void run(){
            while (num < 100) {
                lock.lock();
                try{
                    if(num%2==1){
                        System.out.println("thread2  " + num++);
                    }
                }finally{
                    lock.unlock();
                }
            }
        }
    }
}
