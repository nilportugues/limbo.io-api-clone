package io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the JobOfferJpaModel entity.
 */
@Named("JobOfferJpaRepository")
@SuppressWarnings("unused")
public interface JobOfferJpaRepository extends JpaRepository<JobOfferJpaModel,String>, JpaSpecificationExecutor {


    @Query("select distinct jobOffer from JobOfferJpaModel jobOffer left join fetch jobOffer.talent where jobOffer.id =:id")
    JobOfferJpaModel findOneWithTalent(@Param("id") String id);

    @Query("select jobOffer from JobOfferJpaModel jobOffer where jobOffer.id =:id")
    JobOfferJpaModel findOneWithEagerRelationships(@Param("id") String id);

}