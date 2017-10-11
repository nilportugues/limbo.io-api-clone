package io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the TalentRoleJpaModel entity.
 */
@Named("TalentRoleJpaRepository")
@SuppressWarnings("unused")
public interface TalentRoleJpaRepository extends JpaRepository<TalentRoleJpaModel,String>, JpaSpecificationExecutor {


    @Query("select talentRole from TalentRoleJpaModel talentRole where talentRole.id =:id")
    TalentRoleJpaModel findOneWithEagerRelationships(@Param("id") String id);

}