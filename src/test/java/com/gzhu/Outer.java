package com.gzhu;

import java.util.concurrent.ConcurrentHashMap;

public class Outer {
    public int o = 1;
    private static int o1 = 2;
    public Inner inner = null;
    public static class Inner {
        public int i1 = o1;

        public void in(){
            System.out.println(i1);
            ConcurrentHashMap map = new ConcurrentHashMap();
            map.remove("df");
        }

    }

}
