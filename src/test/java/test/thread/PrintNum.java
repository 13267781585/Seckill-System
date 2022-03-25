package test.thread;

public class PrintNum {
    private static final Object object = new Object();
    private volatile static int num = 0;
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Thread1());
        thread.start();
        Thread thread1 = new Thread(new Thread2());
        thread1.start();
        thread.join();
    }

    public static class Thread2 implements Runnable{
        public void run(){
            while(num<=100){
                synchronized (object)
                {
                    while(num%2==1)
                    {
                        System.out.println("thread2  " + num);
                        num++;
                    }
                    object.notify();
                    try{
                        object.wait();
                    }catch (Exception e){

                    }
                }
            }
            System.out.println("thread2  end");
        }
    }

    public static class Thread1 implements Runnable{
        public void run(){
            while(num<=100){
                synchronized (object)
                {
                    while(num%2==0)
                    {
                        System.out.println("thread1  " + num);
                        num++;
                    }
                    object.notify();
                    try{
                        object.wait();
                    }catch (Exception e){

                    }
                }
            }
            System.out.println("thread1 end..");
        }
    }
}
