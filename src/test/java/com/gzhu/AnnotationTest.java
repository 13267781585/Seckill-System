package com.gzhu;

public @interface AnnotationTest {
    int age() default 16;
    String name();
}
