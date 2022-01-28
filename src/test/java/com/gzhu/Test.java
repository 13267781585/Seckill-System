package com.gzhu;

import java.util.TreeMap;

public class Test {
    public static void main(String[] args) {
        TreeMap<Integer,Integer> map = new TreeMap<>();
        map.put(1,4);
        map.put(2,3);
        map.put(3,2);
        map.put(4,1);
        map.put(5,0);
        System.out.println(map.keySet());
    }
}
