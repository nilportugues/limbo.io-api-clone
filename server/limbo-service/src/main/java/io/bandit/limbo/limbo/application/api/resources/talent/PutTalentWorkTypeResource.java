package io.bandit.limbo.limbo.application.api.resources.talent;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentWorkTypePersist;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.worktype.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.worktype.presenters.WorkTypeOutputBoundary;
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
public class PutTalentWorkTypeResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_PUT_ONE_WORK_TYPES = "Replaces a relationship between talents with a new work type.";

    private final WorkTypeOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PutTalentWorkTypeResource(
        final ICommandBus commandBus,
        @Named("WorkTypeJsonPresenter") final WorkTypeOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_PUT_ONE_WORK_TYPES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_WORKTYPE_ROUTE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = WorkTypeResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = WorkTypeUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = WorkTypeForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = WorkTypeNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = WorkTypeInternalServerError.class)
    })
    public Future<ResponseEntity<String>> update(@PathVariable String id, @RequestBody final WorkTypeData data) {

final TalentWorkTypePersist.Command command = new TalentWorkTypePersist.Command(id, WorkTypeData.toWorkType(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(talent -> ((Talent) talent).getWorkType())
            .map(this::updatedView)
            .publishOn(Schedulers.parallel())
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentWorkTypeNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentWorkTypeError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> updatedView(final WorkType workType) {
        metrics.incrementTalentWorkTypeUpdated();
        return new ResponseEntity<>(presenter.success(workType), HttpStatus.OK);
    }
}
