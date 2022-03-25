package test.shejimoshi;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Jdk implements InvocationHandler {
    private Object target;
    public Jdk(Object t)
    {
        target = t;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before....");
        Object res = method.invoke(target,args);
        System.out.println("after...");
        return res;
    }

    public static void main(String[] args) {
        C c = new A();
        C c1 = new B();
        C proxy = (C) Proxy.newProxyInstance(c.getClass().getClassLoader(),new Class[]{C.class},new Jdk(c));
        proxy.say();
    }
}
