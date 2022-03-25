package test.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition putQueue = lock.newCondition();
    private final Condition getQueue = lock.newCondition();
    private volatile int size = 0;
    public void put(){
        lock.lock();
        try{
            if(size<10){
                System.out.println("成功放入数据!");
                size++;
            }else{
                System.out.println("数据满了！等待！");
                getQueue.signalAll();
                putQueue.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void get(){
        lock.lock();
        try{
            if(size>=1){
                System.out.println("拿到数据！");
                size--;
            }else{
                System.out.println("未拿到数据！唤醒!");
                putQueue.signalAll();
                getQueue.await();
            }
        }catch (Exception e){}
        finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue();
        new Thread(){
            public void run(){
                int count = 0;
                while(count<30){
                    blockingQueue.put();
                    count++;
                }
            }
        }.start();
        new Thread(){
            public void run(){
                int count = 0;
                while(count<30){
                    blockingQueue.get();
                    count++;
                }
            }
        }.start();

    }
}
