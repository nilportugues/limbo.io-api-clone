package io.bandit.limbo.limbo.modules.main.companytraits.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.elasticsearch.CompanyTraitsQueryModel;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.elasticsearch.CompanyTraitsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;


@Named("CompanyTraitsDeletedReadModelHandler")
public class CompanyTraitsDeletedReadModelHandler implements IEventHandler<CompanyTraitsDeleted>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CompanyTraitsQueryRepository companyTraitsQueryRepository;

    @Inject
    public CompanyTraitsDeletedReadModelHandler(
            @Named("CompanyTraitsQueryRepository") final CompanyTraitsQueryRepository companyTraitsQueryRepository) {

        this.companyTraitsQueryRepository = companyTraitsQueryRepository;
    }

    public CompletableFuture<Void> handle(final CompanyTraitsDeleted event){
        return CompletableFuture.runAsync(() -> {
            final CompanyTraitsDeleted.Payload payload = event.getPayload();
            final String companyTraitsId = payload.getId();

            companyTraitsQueryRepository.delete(companyTraitsId);
        });
    }
}
