package test.lock;

public class PrintNum1 {
    public volatile static int flag = 0;
    public volatile static int count = 1;
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
                if(flag==0){
                    System.out.println(count++);
                    flag = 1;
                }
            }
        }
    }

    public static class Thread2 extends Thread{
        public void run(){
            while(count<=100){
                if(flag==1){
                    System.out.println(count++);
                    flag = 2;
                }
            }
        }
    }

    public static class Thread3 extends Thread{
        public void run(){
            while(count<=100){
                if(flag==2){
                    System.out.println(count++);
                    flag = 0;
                }
            }
        }
    }
}
