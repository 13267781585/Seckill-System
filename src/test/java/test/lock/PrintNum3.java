package test.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class PrintNum3 {
    public static final AtomicInteger atomicInteger = new AtomicInteger(1);
    public static void main(String[] args) {
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.get());
        System.out.println(atomicInteger.incrementAndGet());
    }
}
