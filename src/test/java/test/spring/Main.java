package test.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.PriorityQueue;

@ComponentScan("test.spring")
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        context.register(ObjectB.class);

//         ObjectA objectA = (ObjectA) context.getBean("objectA");
//         System.out.println(objectA);
//         System.out.println((ObjectA) context.getBean("objectA"));
//         System.out.println(context.getBean(ObjectA.class));
//        System.out.println(context.getBean(ObjectA.class));

       // System.out.println(objectA);
        ObjectB b = (ObjectB) context.getBean("objectB");
        System.out.println(b);
        context.close();
    }
}
