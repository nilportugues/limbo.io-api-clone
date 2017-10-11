package io.bandit.limbo.limbo.modules.main.talentrole.event;

import io.bandit.limbo.limbo.infrastructure.cqrs.event.IEventHandler;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.elasticsearch.TalentRoleQueryModel;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.elasticsearch.TalentRoleQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Named("TalentRoleTitleChanged.Handler")
public class TalentRoleTitleChangedHandler implements IEventHandler<TalentRoleTitleChanged>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final TalentRoleQueryRepository talentRoleQueryRepository;

    @Inject
    public TalentRoleTitleChangedHandler(@Named("TalentRoleQueryRepository") final TalentRoleQueryRepository talentRoleQueryRepository) {

        this.talentRoleQueryRepository = talentRoleQueryRepository;
    }

    public CompletableFuture<Void> handle(final TalentRoleTitleChanged event) {

        return CompletableFuture.runAsync(() -> {
            final TalentRoleTitleChanged.Payload payload = event.getPayload();
            final TalentRoleTitleChanged.Attributes attributes = payload.getAttributes();
            final String talentRoleId = payload.getId();

            //Custom logic here.

        });
    }
}
