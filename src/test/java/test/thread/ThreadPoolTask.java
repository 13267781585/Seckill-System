package test.thread;

import java.util.concurrent.*;

public class ThreadPoolTask {
    //Future等待返回值是阻塞的
    public static final ArrayBlockingQueue<String> result = new ArrayBlockingQueue<String>(20);
    public static final CountDownLatchExtension<Integer> count = new CountDownLatchExtension<>(10);
    public static final ExecutorService executorService = new ThreadPoolExecutor(11,15,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(10));
    public static final ScheduledExecutorService timeService = Executors.newScheduledThreadPool(5);


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        executorService.execute(new PrintResult());
        //timeService.scheduleAtFixedRate(new TimeTask(),1,5,TimeUnit.SECONDS);
        for(int i=0;i<10;i++){
            executorService.execute(new Task1(i));
        }
    }

    static class TimeTask implements Runnable{
        @Override
        public void run() {
            System.out.println("执行定时任务！！！");
            for(int i=0;i<10;i++){
                String key = "start:" + i;
                if (!result.contains(key))
                {
                    executorService.execute(new Task1(i));
                    System.out.println("补充任务" + i);
                }
            }
        }
    }

    static class PrintResult implements Runnable{

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(result);
            System.out.println("多线程执行任务耗费的时间:" + (System.currentTimeMillis() - start));
            System.out.println("任务完成！关闭定时任务!");
            timeService.shutdown();
        }
    }

    static class Task1 implements Runnable{
        private int id;
        public Task1(int id){
            this.id = id;
        }
        @Override
        public void run(){
            System.out.println("执行任务：" + id);
            try {
                //休眠模拟逻辑处理
                Thread.sleep(1000);
                count.countDown(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            result.add("start:" + id);
        }
    }
}
