package org.bpours.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringManager {

    private static ApplicationContext context = null;

    public static void init() {
        context = new ClassPathXmlApplicationContext("classpath*:spring-*.xml");
    }

    public static <T> T getBean(Class v) {
        return (context.getBean(v) == null) ? null : ((T) context.getBean(v));
    }

}
