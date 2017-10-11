package io.bandit.limbo.limbo.modules.cqrs;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.cqrs.middleware.CommandCounterMetricsMiddleware;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandBusMiddleware
{
    @Bean
    public CommandCounterMetricsMiddleware commandCounterMetricsMiddleware(final ICommandBus commandBus,
                                                                           final MeterRegistry registry) {

        final CommandCounterMetricsMiddleware middleware = new CommandCounterMetricsMiddleware(registry);
        commandBus.addMiddleware(middleware);

        return middleware;
    }

}