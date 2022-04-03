package test.lock;

public class PrintNum2 {
    public static final Object lock = new Object();
    public static volatile int count = 1;

    public static void main(String[] args) {
        Thread thread1 = new Thread1();
        Thread thread2 = new Thread2();
        Thread thread3 = new Thread3();
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public static class Thread1 extends Thread{
        public void run(){
            while(count<=100){
                synchronized (lock)
                {
                    try{
                        if(count%3==1){
                            System.out.println(count++);
                        }
                        lock.notifyAll();
                        if(count<100)
                            lock.wait();
                    }catch (Exception e)
                    {

                    }
                }
            }
        }
    }

    public static class Thread2 extends Thread{
        public void run(){
            while(count<=100){
                synchronized (lock)
                {
                    try{
                        if(count%3==2){
                            System.out.println(count++);
                        }
                        lock.notifyAll();
                        if(count<100)
                            lock.wait();
                    }catch (Exception e)
                    {

                    }
                }
            }
        }
    }

    public static class Thread3 extends Thread{
        public void run(){
            while(count<=100){
                synchronized (lock)
                {
                    try{
                        if(count%3==0){
                            System.out.println(count++);
                        }
                        lock.notifyAll();
                        if(count<100)
                            lock.wait();
                    }catch (Exception e)
                    {

                    }
                }
            }
        }
    }


}
