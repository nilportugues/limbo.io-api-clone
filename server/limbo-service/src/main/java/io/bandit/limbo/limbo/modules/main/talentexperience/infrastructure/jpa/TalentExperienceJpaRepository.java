package io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the TalentExperienceJpaModel entity.
 */
@Named("TalentExperienceJpaRepository")
@SuppressWarnings("unused")
public interface TalentExperienceJpaRepository extends JpaRepository<TalentExperienceJpaModel,String>, JpaSpecificationExecutor {


    @Query("select talentExperience from TalentExperienceJpaModel talentExperience where talentExperience.id =:id")
    TalentExperienceJpaModel findOneWithEagerRelationships(@Param("id") String id);

}