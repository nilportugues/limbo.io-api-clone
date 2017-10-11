package io.bandit.limbo.limbo.infrastructure.cqrs.command;

import io.bandit.limbo.limbo.infrastructure.cqrs.resolvers.ResolverStrategy;
import io.bandit.limbo.limbo.infrastructure.cqrs.di.IBeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class CommandRegistry  {

    private static final Logger log = LoggerFactory.getLogger(CommandRegistry.class);
    private static final String LOG = "[Command Bus][Registered] {}";

    private final Map<String, String> commands;
    private final ResolverStrategy resolver;
    private final IBeanProvider provider;

    public CommandRegistry(final IBeanProvider provider, final ResolverStrategy resolver) {
        this.commands = new HashMap<>();
        this.provider = provider;
        this.resolver = resolver;
    }

    public void register(final Class<? extends ICommand> command, final ICommandHandler handler) {
        final String commandName = resolver.get(command);
        final String handlerBeanName = resolver.get(handler);

        registerHandler(handlerBeanName, commandName);
        logRegistration(commandName);
    }

    public void register(final Class<? extends ICommand> command, final String handlerBeanName) {
        final String commandName = resolver.get(command);

        registerHandler(handlerBeanName, commandName);
        logRegistration(commandName);
    }

    private void registerHandler(final String handlerBeanName, final String commandName) {
        commands.put(commandName, handlerBeanName);
    }

    private void logRegistration(final String commandName) {
        log.debug(LOG, commandName);
    }

    public ICommandHandler get(final ICommand command) {
        final String commandClassName = resolver.get(command);
        final String commandHandlerClassName = commands.get(commandClassName);

        return (ICommandHandler) provider.get(commandHandlerClassName);
    }
}
