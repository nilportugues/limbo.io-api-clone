package io.bandit.limbo.limbo.infrastructure.cqrs.query;


public interface IQueryResponse<Data> {
    Data getResponse();
}
