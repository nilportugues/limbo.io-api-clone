package io.bandit.limbo.limbo.application.api.resources.talent;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentTalentRolePersist;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.talentrole.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talentrole.presenters.TalentRoleOutputBoundary;
import io.bandit.limbo.limbo.modules.shared.model.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;
import java.net.URI;
import java.util.Optional;

// _EntityRelationshipResourceOneToMany.java
@RestController
public class PutTalentTalentRoleResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_PUT_ONE_TALENT_ROLES = "Replaces a relationship between talents with a new talent role.";

    private final TalentRoleOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PutTalentTalentRoleResource(
        final ICommandBus commandBus,
        @Named("TalentRoleJsonPresenter") final TalentRoleOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_PUT_ONE_TALENT_ROLES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_TALENTROLE_ROUTE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentRoleResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentRoleUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentRoleForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentRoleNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentRoleInternalServerError.class)
    })
    public Future<ResponseEntity<String>> update(@PathVariable String id, @RequestBody final TalentRoleData data) {

final TalentTalentRolePersist.Command command = new TalentTalentRolePersist.Command(id, TalentRoleData.toTalentRole(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(talent -> ((Talent) talent).getTalentRole())
            .map(this::updatedView)
            .publishOn(Schedulers.parallel())
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentTalentRoleNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentTalentRoleError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> updatedView(final TalentRole talentRole) {
        metrics.incrementTalentTalentRoleUpdated();
        return new ResponseEntity<>(presenter.success(talentRole), HttpStatus.OK);
    }
}
