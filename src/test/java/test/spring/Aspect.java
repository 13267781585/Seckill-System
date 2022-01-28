package test.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.Before;
import org.springframework.stereotype.Component;

@Component
@org.aspectj.lang.annotation.Aspect
public class Aspect {
    @Before
    public void before(JoinPoint joinPoint){
        System.out.println("before....");
        System.out.println(joinPoint.toString());
    }

    @Pointcut("execution(* test.spring.*.*(..))")
    public void pcut(){

    }

    @AfterReturning
    public void returning(){
        System.out.println("returning...");
    }
}
