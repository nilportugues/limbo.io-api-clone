package io.bandit.limbo.limbo.modules.main.companytraits.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.elasticsearch.CompanyTraitsQueryModel;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.elasticsearch.CompanyTraitsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.concurrent.CompletableFuture;


@Named("CompanyTraitsCreated.ReadModelHandler")
public class CompanyTraitsCreatedReadModelHandler implements IEventHandler<CompanyTraitsCreated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CompanyTraitsQueryRepository companyTraitsQueryRepository;

    @Inject
    public CompanyTraitsCreatedReadModelHandler(
            @Named("CompanyTraitsQueryRepository") final CompanyTraitsQueryRepository companyTraitsQueryRepository) {

        this.companyTraitsQueryRepository = companyTraitsQueryRepository;
    }

    public CompletableFuture<Void> handle(final CompanyTraitsCreated event) {
        return CompletableFuture.runAsync(() -> {
            final CompanyTraitsCreated.Payload payload = event.getPayload();
            final CompanyTraitsCreated.Attributes attributes = payload.getAttributes();
            final CompanyTraitsQueryModel companyTraits = new CompanyTraitsQueryModel();

            companyTraits.setId(payload.getId());
            companyTraits.setTitle(attributes.getTitle());
            companyTraits.setTalent(attributes.getTalent());

            companyTraitsQueryRepository.save(companyTraits);
        });
    }
}
