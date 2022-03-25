package test;

import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        ThreadLocal<Integer> i = new ThreadLocal<>();
        i.set(1);
        i.get();
    }

}