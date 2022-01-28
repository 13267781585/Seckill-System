package test.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@Component
//@Scope("prototype")
public class ObjectB {
    private char ch;
    public ObjectB(){
        System.out.println("construction...");
    }

//    @Override
//    public void setBeanName(String s) {
//        System.out.println("beanName:" + s);
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        System.out.println("ApplicationContext....");
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("afterPropertiesSet...");
//    }
//
//    @PostConstruct
//    public void initMethod(){
//        System.out.println("postConstruct...");
//    }
//
//    @PreDestroy
//    public void destroy1(){
//        System.out.println("destroy11....");
//    }
//
//    public void destroy(){
//        System.out.println("destroy....");
//    }
}
