package test.shejimoshi;

import java.lang.reflect.Constructor;
import java.util.concurrent.ArrayBlockingQueue;

public class StaticClass {
    private StaticClass() throws Exception {
        if(flag){
            throw new Exception("破坏单例模式!");
        }
    }
    private static boolean flag = false;
    private static volatile StaticClass instance = null;

    public static StaticClass getInstance() throws Exception {
        if(instance==null)
        {
            synchronized (StaticClass.class)
            {
                if(instance==null){
                    instance = new StaticClass();
                    flag = true;
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws Exception {
        StaticClass instance = StaticClass.getInstance();
        System.out.println(instance);
        Constructor<StaticClass> constructor = StaticClass.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        StaticClass instance1 = constructor.newInstance();
        System.out.println(instance1);
    }

}
