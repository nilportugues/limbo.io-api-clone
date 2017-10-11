package io.bandit.limbo.limbo.infrastructure.cqrs.di;

public interface IBeanProvider<T> {
    T get(final String beanName);
}
