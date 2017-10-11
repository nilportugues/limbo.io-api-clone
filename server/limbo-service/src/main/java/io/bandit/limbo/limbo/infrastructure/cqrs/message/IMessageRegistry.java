package io.bandit.limbo.limbo.infrastructure.cqrs.message;

public interface IMessageRegistry<M extends IMessage, MH> {
    void register(final M message, final MH handler);
    MH get(final String name);
}
