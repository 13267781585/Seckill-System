package test.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

//@Component
public class DestructionAwareBean implements DestructionAwareBeanPostProcessor {
    @Override
    public void postProcessBeforeDestruction(Object o, String s) throws BeansException {
        System.out.println("postProcessBeforeDestruction...");
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return true;
    }
}
