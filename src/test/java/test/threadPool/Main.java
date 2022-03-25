package test.threadPool;

import java.util.BitSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3,4,3, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2),new RejectHandler());
////        for(int i=0;i<10;i++)
////        {
////            Future<String> res = poolExecutor.submit(()->{return "dsfd";});
////            res.get();
////        }
        add(111,899);
    }

    public static int add(int a, int b) {
        //47
        int mask = 1;
        int res = 0;
        boolean flag = false;
        for(int i=0;i<32;i++){
            int num1 = a&mask;
            int num2 = b&mask;
            System.out.println(Integer.toBinaryString(num1));
            System.out.println(Integer.toBinaryString(num2));
            if(num1!=0&&num2!=0){
                if(flag){
                    res |= mask;
                }
                flag = true;
            }else if((num1==0&&num2!=0)||(num1!=0&&num2==0)){
                if(!flag){
                    res |= mask;
                }
            }else{
                
                flag = false;
            }
            mask = mask<<1;
        }
        return res;
    }

}
