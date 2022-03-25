package test.lock;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.lockInterruptibly();
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        reentrantReadWriteLock.readLock().lock();
        reentrantReadWriteLock.readLock().unlock();
        reentrantReadWriteLock.writeLock().lock();
        reentrantReadWriteLock.writeLock().unlock();
    }
    private class Syn extends AbstractQueuedLongSynchronizer{
        @Override
        protected boolean tryAcquire(long arg) {
            return super.tryAcquire(arg);
        }

        @Override
        protected boolean tryRelease(long arg) {
            return super.tryRelease(arg);
        }
    }

    private Syn syn;

    public void countDown(int n){
        syn.tryAcquire(n);

    }

    public void await(){

    }
}
