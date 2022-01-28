package com.gzhu;

public class Singleton {
    private static final Singleton singleton= new Singleton();
    private Singleton(){}

    public static Singleton getInstance(){
        return singleton;
    }

    @Override
    public Object clone(){
        return singleton;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Singleton singleton = Singleton.getInstance();
        System.out.println(singleton);
        Singleton singleton1 = (Singleton) singleton.clone();
        System.out.println(singleton1);
    }
}
