package test.shejimoshi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

public class A implements C {
    @Override
    public Object say() {
        System.out.println("say....");
        return null;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        LinkedList<int[]> list1 =  new LinkedList<>();
        list1.add(new int[]{1,2});
    }
}
