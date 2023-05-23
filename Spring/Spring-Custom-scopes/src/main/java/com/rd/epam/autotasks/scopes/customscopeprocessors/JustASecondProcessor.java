package com.rd.epam.autotasks.scopes.customscopeprocessors;

import com.rd.epam.autotasks.scopes.customscopes.JustASecondScope;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class JustASecondProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        factory.registerScope("justASecond", new JustASecondScope());
    }
}
