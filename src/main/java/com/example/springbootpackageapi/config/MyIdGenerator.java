package com.example.springbootpackageapi.config;

import lombok.SneakyThrows;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MyIdGenerator implements IdentifierGenerator, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private final Map<String, Long> lastGeneratedIds = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @SneakyThrows
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String className = object.getClass().getSimpleName();
        String repositoryName = className + "Repository";
        Class<?> repositoryClass = Class.forName("com.example.springbootpackageapi.repositories." + repositoryName);
        Object repositoryInstance = applicationContext.getBean(repositoryClass);

        Long lastGeneratedId = lastGeneratedIds.get(className);
        if (lastGeneratedId == null) {
            Method method = repositoryClass.getMethod("findByMaxId");
            Object result = method.invoke(repositoryInstance);
            lastGeneratedId = result == null ? 0L : (Long) result;
        }

        lastGeneratedId++;
        lastGeneratedIds.put(className, lastGeneratedId);

        return lastGeneratedId;
    }

}
