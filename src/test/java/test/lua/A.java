package test.lua;

import java.util.HashMap;
import java.util.HashSet;

public class A {
    public int a;

    @Override
    public boolean equals(Object obj) {
        return this.a == ((A)obj).a;
    }

    @Override
    public int hashCode() {
        return a;
    }

    public static void main(String[] args) {
        HashMap<A,Integer> hashMap = new HashMap<>();
        A a = new A();
        a.a = 1;
        hashMap.put(a,1);
        hashMap.put(a,1);
        A a1 = new A();
        a1.a = 1;
        hashMap.put(a1,1);
        hashMap.put(a1,1);
        System.out.println(hashMap.size());
    }
}
