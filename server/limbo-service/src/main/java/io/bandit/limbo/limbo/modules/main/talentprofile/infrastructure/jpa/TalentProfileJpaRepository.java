package io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the TalentProfileJpaModel entity.
 */
@Named("TalentProfileJpaRepository")
@SuppressWarnings("unused")
public interface TalentProfileJpaRepository extends JpaRepository<TalentProfileJpaModel,String>, JpaSpecificationExecutor {


    @Query("select talentProfile from TalentProfileJpaModel talentProfile where talentProfile.id =:id")
    TalentProfileJpaModel findOneWithEagerRelationships(@Param("id") String id);

}