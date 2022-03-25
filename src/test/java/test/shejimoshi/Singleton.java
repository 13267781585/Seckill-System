package test.shejimoshi;

import java.lang.reflect.Constructor;

public class Singleton {

    public static void main(String[] args) throws Exception{
        Singleton singleton = getInstance1();
        Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor(null);
        Singleton singleton1 = constructor.newInstance();
        System.out.println(singleton1);
    }

    private Singleton(){
        synchronized (Singleton.class){
            if(instance1!=null){
                System.out.println("hhhhhhhhhhhh");
            }
        }
    }
    //饿汉
    private static final Singleton instance = new Singleton();
    public Singleton getInstance(){
        return instance;
    }

    //懒汉
    private static volatile Singleton instance1 = null;

    public synchronized static Singleton getInstance1(){
        if(instance1==null)
        {
            instance1 = new Singleton();
        }
        return instance1;
    }

    public static Singleton getInstance2(){
        if(instance1==null){
            synchronized (Singleton.class){
                if(instance1==null){
                    instance1 = new Singleton();
                }
            }
        }
        return instance1;
    }

}
