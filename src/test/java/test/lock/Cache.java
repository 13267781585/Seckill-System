package test.lock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {
    private static boolean flag;
    private static Object data;
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

}
