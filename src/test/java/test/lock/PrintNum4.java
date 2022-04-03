package test.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PrintNum4 {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition1 = lock.newCondition();
    private static final Condition condition2 = lock.newCondition();
    private static final Condition condition3 = lock.newCondition();

    private static volatile int count = 1;

    public static void main(String[] args) throws Exception {
        Thread thread1 = new Thread1();
        Thread thread2 = new Thread2();
        Thread thread3 = new Thread3();
        thread3.start();
        Thread.sleep(1000);
        thread2.start();
        Thread.sleep(1000);
        thread1.start();
    }

    public static class Thread1 extends Thread{
        public void run(){
            while(count<=100){
                try{
                    lock.lock();
                    if(count%3==1){
                        System.out.println(count++);
                    }
                    condition2.signal();

                    if(count<100){
                        condition1.await();
                    }
                }catch (Exception e)
                {

                }finally {
                    lock.unlock();
                }
            }
        }
    }

    public static class Thread2 extends Thread{
        public void run(){
            while(count<=100){
                try{
                    lock.lock();
                    if(count%3==2){
                        System.out.println(count++);
                    }
                    condition3.signal();

                    if(count<100){
                        condition2.await();
                    }
                }catch (Exception e)
                {

                }finally {
                    lock.unlock();
                }
            }
        }
    }

    public static class Thread3 extends Thread{
        public void run(){
            while(count<=100){
                try{
                    lock.lock();
                    if(count%3==0){
                        System.out.println(count++);
                    }
                    condition1.signal();

                    if(count<100){
                        condition3.await();
                    }
                }catch (Exception e)
                {

                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
