package test.thread;

import java.util.concurrent.*;

public class SingleTask {
    public static final ExecutorService service = new ThreadPoolExecutor(1,1,10, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        for(int i=0;i<10;i++){
            Future<Integer> result = service.submit(new Task1(i));
            result.get();
        }
        System.out.println("单个任务执行耗费时间:" + (System.currentTimeMillis() - start));
    }

    static class Task1 implements Callable<Integer>{
        private int id;
        public Task1(int id){
            this.id = id;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("执行任务：" + id);
            try {
                //休眠模拟逻辑处理
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return id;
        }
    }
}
