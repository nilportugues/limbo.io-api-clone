package io.bandit.limbo.limbo.infrastructure.cqrs;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.CommandRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.infrastructure.cqrs.di.IBeanProvider;
import io.bandit.limbo.limbo.infrastructure.cqrs.di.SpringBeanProvider;
import io.bandit.limbo.limbo.infrastructure.cqrs.event.EventRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.IQueryBus;
import io.bandit.limbo.limbo.infrastructure.cqrs.query.QueryRegistry;
import io.bandit.limbo.limbo.infrastructure.cqrs.resolvers.ResolverStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CQRSModule {

    @Bean
    public IBeanProvider getSpringBeanProvider(final ApplicationContext context) {
        return new SpringBeanProvider(context);
    }

    @Bean
    public ResolverStrategy getResolverStrategy() {
        return new ResolverStrategy();
    }

    @Bean
    public CommandRegistry getCommandRegistry(final ResolverStrategy resolver, final IBeanProvider provider) {
        return new CommandRegistry(provider, resolver);
    }

    @Bean
    public ICommandBus getCommandBus(final CommandRegistry registry, final ResolverStrategy resolver) {
        return new CommandBus(registry, resolver);
    }

    @Bean
    public QueryRegistry getQueryRegistry(final ResolverStrategy resolver, final IBeanProvider provider) {
        return new QueryRegistry(provider, resolver);
    }

    @Bean
    public IQueryBus getQueryBus(final QueryRegistry registry, final ResolverStrategy resolver) {
        return new QueryBus(registry, resolver);
    }

    @Bean
    public EventRegistry getEventRegistry(final ResolverStrategy resolver, final IBeanProvider provider) {
        return new EventRegistry(provider, resolver);
    }

    @Bean
    public EventBus getEventBus(final EventRegistry registry, final ResolverStrategy resolver) {
        return new EventBus(registry, resolver);
    }
}