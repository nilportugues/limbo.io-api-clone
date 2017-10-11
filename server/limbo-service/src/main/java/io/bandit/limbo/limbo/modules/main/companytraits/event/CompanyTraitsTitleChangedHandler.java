package io.bandit.limbo.limbo.modules.main.companytraits.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.elasticsearch.CompanyTraitsQueryModel;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.elasticsearch.CompanyTraitsQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("CompanyTraitsTitleChanged.Handler")
public class CompanyTraitsTitleChangedHandler implements IEventHandler<CompanyTraitsTitleChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final CompanyTraitsQueryRepository companyTraitsQueryRepository;

    @Inject
    public CompanyTraitsTitleChangedHandler(@Named("CompanyTraitsQueryRepository") final CompanyTraitsQueryRepository companyTraitsQueryRepository) {

        this.companyTraitsQueryRepository = companyTraitsQueryRepository;
    }

    public CompletableFuture<Void> handle(final CompanyTraitsTitleChanged event) {

        return CompletableFuture.runAsync(() -> {
            final CompanyTraitsTitleChanged.Payload payload = event.getPayload();
            final CompanyTraitsTitleChanged.Attributes attributes = payload.getAttributes();
            final String companyTraitsId = payload.getId();

            //Custom logic here.

        });
    }
}
