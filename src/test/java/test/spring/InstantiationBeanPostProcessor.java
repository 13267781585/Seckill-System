//package test.spring;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.PropertyValues;
//import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
//import org.springframework.stereotype.Component;
//
//import java.beans.PropertyDescriptor;
//
//public class InstantiationBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
//    @Override
//    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
//        System.out.println("postProcessBeforeInstantiation....");
//        return null;
//    }
//
//    @Override
//    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
//        System.out.println("postProcessAfterInstantiation....");
//        return false;
//    }
//
//    @Override
//    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
//        System.out.println("postProcessPropertyValues....");
//        return null;
//    }
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("postProcessBeforeInitialization....");
//        return null;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("postProcessAfterInitialization...");
//        return null;
//    }
//}
