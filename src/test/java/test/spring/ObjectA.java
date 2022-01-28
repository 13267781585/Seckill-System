package test.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component
//@Scope("prototype")
public class ObjectA {
    private int age;
    private String name;
    @Autowired
    private ObjectB objectB;

//    @Override
//    public String toString() {
//        return "ObjectA{" +
//                "age=" + age +
//                ", name='" + name + '\'' +
//                ", objectB=" + objectB +
//                '}';
//    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectB getObjectB() {
        return objectB;
    }

    public void setObjectB(ObjectB objectB) {
        this.objectB = objectB;
    }
}
