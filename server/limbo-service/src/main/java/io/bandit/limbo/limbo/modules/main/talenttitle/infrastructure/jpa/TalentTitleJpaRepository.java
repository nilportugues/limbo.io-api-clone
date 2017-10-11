package io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the TalentTitleJpaModel entity.
 */
@Named("TalentTitleJpaRepository")
@SuppressWarnings("unused")
public interface TalentTitleJpaRepository extends JpaRepository<TalentTitleJpaModel,String>, JpaSpecificationExecutor {


    @Query("select talentTitle from TalentTitleJpaModel talentTitle where talentTitle.id =:id")
    TalentTitleJpaModel findOneWithEagerRelationships(@Param("id") String id);

}