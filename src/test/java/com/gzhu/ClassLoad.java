package com.gzhu;
import java.util.ArrayList;

public class ClassLoad {
    public static void main(String[] args) throws Exception {
        ArrayList<String>[] list = new ArrayList[2];
        System.out.println(list.getClass().getTypeName());
        ArrayList<Integer> l1 = new ArrayList<>();
        l1.add(1);
        Object o = list;
        Object[] objects = (Object[]) o;
        objects[0]= l1;
        String value = list[0].get(0);
    }
}
