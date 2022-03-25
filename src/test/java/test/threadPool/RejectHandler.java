package test.threadPool;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        throw new MyException("拒绝策略！");
    }


    class MyException extends RuntimeException{

        public MyException(String s) {
            super(s);
        }
    }
}
