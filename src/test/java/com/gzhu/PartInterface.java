package com.gzhu;

public interface PartInterface {
    void out();

    public static void main(String[] args) {
        class A implements PartInterface{

            @Override
            public void out() {
                System.out.println("A...");
            }
        }

        class B implements PartInterface{

            @Override
            public void out() {
                System.out.println("B....");
            }
        }

        A a = new A();
        a.out();
        B b = new B();
        b.out();
    }
}
