package test.lock;

import java.util.concurrent.locks.*;

public class PrintNum {
    private static volatile int count = 1;

    public static void main(String[] args) throws InterruptedException {
        PrintThread printThread = new PrintThread();
        Thread thread1 = new Thread(printThread);
        Thread thread2 = new Thread(printThread);
        Thread thread3 = new Thread(printThread);
        thread1.start();
        thread2.start();
        thread3.start();
        while(count<=100){
            int pos = count % 3;
            if(pos==1){
                LockSupport.unpark(thread1);
            }else if(pos==2)
            {
                LockSupport.unpark(thread2);
            }else{
                LockSupport.unpark(thread3);
            }
        }
        Thread.sleep(1000);
        LockSupport.unpark(thread1);
        LockSupport.unpark(thread2);
        LockSupport.unpark(thread3);
    }

    public static class PrintThread implements Runnable{
        public void run(){
            while(count<=100){
                LockSupport.park();
                if(count<=100)
                    System.out.println(count++);
            }
        }
    }
}
