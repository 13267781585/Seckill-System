package test.shejimoshi;

import java.lang.reflect.Constructor;

public class Singleton1 {
    public static void main(String[] args) throws Exception {
        Instance instance = Singleton1.getInstance();
        System.out.println(instance);
        Constructor<Instance> constructor = Instance.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        System.out.println(constructor.newInstance());
    }

    public static Instance instance = null;
    private static volatile boolean flag = false;

    public static Instance getInstance() throws Exception {
        if(instance==null){
            synchronized (Instance.class)
            {
                if(instance==null){
                    instance = new Instance();
                    flag = true;
                }
            }
        }
        return instance;
    }

    private static class Instance{
        static {
            System.out.println("instance ...");
        }
        private Instance() throws Exception {
            if(flag){
                throw new Exception("!!!!!");
            }
        }
    }
}
