package com.rd.epam.autotasks.scopes.config;

import com.rd.epam.autotasks.scopes.customscopeprocessors.ThreadProcessor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadScopeConfig {

    @Bean
    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return new ThreadProcessor();
    }

}
