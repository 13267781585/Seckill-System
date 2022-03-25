package test.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

public class Synchronized {
    private static Object object = new Object();
    public static void main(String[] args) throws Exception{
        Object o = new Object();
        Class<Object> clazz = Object.class;
        Class<?> clazz1 = o.getClass();
        Class<?> clazz2 = Class.forName("java.lang.Object");
        Object o1 = clazz.newInstance();
        Object o2 = clazz1.newInstance();
        Object o3 = new CloneClass().clone();
    }

    public static class CloneClass{
        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
}
