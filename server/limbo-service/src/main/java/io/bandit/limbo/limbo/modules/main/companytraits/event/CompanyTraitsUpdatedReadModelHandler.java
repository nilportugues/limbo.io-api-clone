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


@Named("CompanyTraitsUpdated.ReadModelHandler")
public class CompanyTraitsUpdatedReadModelHandler implements IEventHandler<CompanyTraitsUpdated> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CompanyTraitsQueryRepository companyTraitsQueryRepository;

    @Inject
    public CompanyTraitsUpdatedReadModelHandler(
            @Named("CompanyTraitsQueryRepository") final CompanyTraitsQueryRepository companyTraitsQueryRepository) {

        this.companyTraitsQueryRepository = companyTraitsQueryRepository;
    }

    public CompletableFuture<Void> handle(final CompanyTraitsUpdated event) {
        return CompletableFuture.runAsync(() -> {
            final CompanyTraitsUpdated.Payload payload = event.getPayload();
            final CompanyTraitsUpdated.Attributes attributes = payload.getAttributes();

            final CompanyTraitsQueryModel companyTraits = companyTraitsQueryRepository.findOne(payload.getId());

            companyTraits.setTitle(attributes.getTitle());
            companyTraits.setTalent(attributes.getTalent());

            companyTraitsQueryRepository.save(companyTraits);
        });
    }
}
