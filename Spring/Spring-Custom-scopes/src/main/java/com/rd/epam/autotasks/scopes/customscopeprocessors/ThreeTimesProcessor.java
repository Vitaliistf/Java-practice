package com.rd.epam.autotasks.scopes.customscopeprocessors;

import com.rd.epam.autotasks.scopes.customscopes.ThreeTimesScope;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class ThreeTimesProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        factory.registerScope("threeTimes", new ThreeTimesScope());
    }
}
