package com.picc.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;

public class SpringBeanUtil  implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    private static SpringBeanUtil beanUtil = new SpringBeanUtil();

    private SpringBeanUtil(){
        if(applicationContext == null){
            newInstance();
        }
    }

    public void setApplicationContext(ApplicationContext paramApplicationContext) throws BeansException {
        applicationContext = paramApplicationContext;
    }

    public static SpringBeanUtil getInstance(){
        return beanUtil;
    }

    private static ApplicationContext newInstance() {
        if (applicationContext == null) {
            applicationContext = ContextLoader.getCurrentWebApplicationContext();
        }
        return applicationContext;
    }

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public <T> T getBean(Class<T> beanType) {
        return (T) applicationContext.getBean(beanType);
    }

    public <T> T getBean(Class<T> beanType, boolean includeNonSingletons, boolean allowEagerInit) {
        return (T) BeanFactoryUtils.beanOfType(applicationContext, beanType, includeNonSingletons, allowEagerInit);
    }

    public String getMessage(String propertyKey) {
        return getMessage(propertyKey, null);
    }

    public String getMessage(String propertyKey, Object[] args) {
        return applicationContext.getMessage(propertyKey, args, null);
    }
}
