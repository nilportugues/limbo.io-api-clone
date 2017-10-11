package io.bandit.limbo.limbo.application.api.resources.talent;
import io.bandit.limbo.limbo.infrastructure.cqrs.command.ICommandBus;
import io.bandit.limbo.limbo.modules.main.talent.commands.TalentTalentProfilePersist;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.application.api.resources.talent.marshallers.*;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.marshallers.*;
import io.bandit.limbo.limbo.application.api.resources.talent.routing.TalentHttpRouter;
import io.bandit.limbo.limbo.application.api.resources.talent.metrics.TalentApiMetrics;
import io.bandit.limbo.limbo.application.api.resources.talentprofile.presenters.TalentProfileOutputBoundary;
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
public class PutTalentTalentProfileResource {

    private static final String SWAGGER_DOC_TAG = "Talent";
    private static final String SWAGGER_DOC_PUT_ONE_TALENT_PROFILES = "Replaces a relationship between talents with a new talent profile.";

    private final TalentProfileOutputBoundary presenter;
    private final ICommandBus commandBus;
    private final TalentApiMetrics metrics;

    @Inject
    public PutTalentTalentProfileResource(
        final ICommandBus commandBus,
        @Named("TalentProfileJsonPresenter") final TalentProfileOutputBoundary presenter,
        final TalentApiMetrics metrics) {

        this.presenter = presenter;
        this.commandBus = commandBus;
        this.metrics = metrics;
    }

    @ApiOperation(value = SWAGGER_DOC_PUT_ONE_TALENT_PROFILES, tags = {SWAGGER_DOC_TAG})
    @RequestMapping(value = TalentHttpRouter.TALENT_TALENTPROFILE_ROUTE, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = TalentProfileResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = TalentProfileUnauthorized.class),
        @ApiResponse(code = 403, message = "Forbidden", response = TalentProfileForbidden.class),
        @ApiResponse(code = 404, message = "Not found", response = TalentProfileNotFound.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = TalentProfileInternalServerError.class)
    })
    public Future<ResponseEntity<String>> update(@PathVariable String id, @RequestBody final TalentProfileData data) {

final TalentTalentProfilePersist.Command command = new TalentTalentProfilePersist.Command(id, TalentProfileData.toTalentProfile(data));

        return Mono.fromFuture(commandBus.execute(command))
            .map(talent -> ((Talent) talent).getTalentProfile())
            .map(this::updatedView)
            .publishOn(Schedulers.parallel())
            .doOnError(this::errorView)
            .defaultIfEmpty(notFoundView(id))
            .toFuture();
    }

    private ResponseEntity<String> notFoundView(final String talentId) {
        metrics.incrementTalentTalentProfileNotFound();
        return new ResponseEntity<>(presenter.notFound(talentId), HttpStatus.NOT_FOUND);
    }

    private Mono<ResponseEntity<String>> errorView(final Throwable throwable) {
        metrics.incrementTalentTalentProfileError();
        return Mono.just(new ResponseEntity<>(presenter.internalServerError(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ResponseEntity<String> updatedView(final TalentProfile talentProfile) {
        metrics.incrementTalentTalentProfileUpdated();
        return new ResponseEntity<>(presenter.success(talentProfile), HttpStatus.OK);
    }
}
