package io.bandit.limbo.limbo.modules.main.worktype.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommand;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandHandler;
import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.worktype.event.WorkTypeDeleted;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.WorkTypeRepository;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.CompletableFuture;

public class WorkTypeDelete {

    public static class Command implements ICommand {
         private String workTypeId;

         public Command(final String workTypeId) {
             this.workTypeId = workTypeId;
         }

         public String getWorkTypeId() {
             return workTypeId;
        }
    }

    @Named("WorkTypeDelete.CommandHandler")
    public static class CommandHandler implements ICommandHandler<Command> {
        private final WorkTypeRepository workTypeRepository;
        private final EventBus eventBus;

        @Inject
        public CommandHandler(final WorkTypeRepository workTypeRepository, final EventBus eventBus) {
            this.workTypeRepository = workTypeRepository;
            this.eventBus = eventBus;
        }

        /**
         * Delete the WorkType by id.
         */
        public CompletableFuture<WorkType> handle(final Command command) {
        //public Mono<WorkType> delete(final Command command) {
            return CompletableFuture.supplyAsync(() -> {
                final WorkType workType = workTypeRepository.findOne(command.getWorkTypeId());

                if (null != workType) {
                    this.removeDomainRelationships(workType);
                    this.removePersistenceRelationships(workType);
                    this.raiseDeletedEvent(workType);
                }

                return workType;
            });
        }

        private void removeDomainRelationships(final WorkType workType) {
        }

        private void removePersistenceRelationships(final WorkType workType) {
            workTypeRepository.save(workType);
            workTypeRepository.delete(workType.getId());
        }

        private void raiseDeletedEvent(final WorkType workType) {
            eventBus.dispatch(new WorkTypeDeleted(workType));
        }
    }

}
