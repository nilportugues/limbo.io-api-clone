package io.bandit.limbo.limbo.modules.main.talenttitle.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.event.TalentTitleDeleted;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.TalentTitleRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class TalentTitleDelete {

    public static class Command implements ICommand {
         private String talentTitleId;

         public Command(final String talentTitleId) {
             this.talentTitleId = talentTitleId;
         }

         public String getTalentTitleId() {
             return talentTitleId;
        }
    }

    @Named("TalentTitleDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final TalentTitleRepository talentTitleRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final TalentTitleRepository talentTitleRepository, final EventBus eventBus) {
            this.talentTitleRepository = talentTitleRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the TalentTitle by id.
         */
        public CompletableFuture<TalentTitle> handle(final Command command) {
        //public Mono<TalentTitle> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final TalentTitle talentTitle = talentTitleRepository.findOne(command.getTalentTitleId());

                if (null != talentTitle) {
                    this.removeDomainRelationships(talentTitle);
                    this.removePersistenceRelationships(talentTitle);
                    this.raiseDeletedEvent(talentTitle);
                }

                return talentTitle;
            });
        }

        private void removeDomainRelationships(final TalentTitle talentTitle) {
        }

        private void removePersistenceRelationships(final TalentTitle talentTitle) {
            talentTitleRepository.save(talentTitle);
            talentTitleRepository.delete(talentTitle.getId());
        }

        private void raiseDeletedEvent(final TalentTitle talentTitle) {
            eventBus.dispatch(new TalentTitleDeleted(talentTitle));
        }
    }

}
