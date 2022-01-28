package test.thread;

import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExtension<T> extends CountDownLatch {
    public CountDownLatchExtension(int count) {
        super(count);
    }

    private HashSet<T> set = new HashSet<>();
    /**
     * 在技术之前加上标识，防止一个任务多次计数
     */
    public void countDown(T id) {
        if(set.add(id)){
            System.out.println("id:" + id + "成功计数！");
            super.countDown();
        }else{
            System.out.println("id：" + id + "重复计数！");
        }
    }

    public void reset(){
        set.clear();
    }
}
