package io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa.SkillsJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the SkillsJpaModel entity.
 */
@Named("SkillsJpaRepository")
@SuppressWarnings("unused")
public interface SkillsJpaRepository extends JpaRepository<SkillsJpaModel,String>, JpaSpecificationExecutor {


    @Query("select distinct skills from SkillsJpaModel skills left join fetch skills.talent where skills.id =:id")
    SkillsJpaModel findOneWithTalent(@Param("id") String id);

    @Query("select skills from SkillsJpaModel skills where skills.id =:id")
    SkillsJpaModel findOneWithEagerRelationships(@Param("id") String id);

}