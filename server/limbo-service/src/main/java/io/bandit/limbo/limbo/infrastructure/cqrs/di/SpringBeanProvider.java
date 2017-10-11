package io.bandit.limbo.limbo.infrastructure.cqrs.di;

import org.springframework.context.ApplicationContext;

public class SpringBeanProvider implements IBeanProvider<Object> {

    private final ApplicationContext context;

    public SpringBeanProvider(final ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object get(final String beanName) {
        return context.getBean(beanName);
    }
}
