package io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the NotableProjectsJpaModel entity.
 */
@Named("NotableProjectsJpaRepository")
@SuppressWarnings("unused")
public interface NotableProjectsJpaRepository extends JpaRepository<NotableProjectsJpaModel,String>, JpaSpecificationExecutor {


    @Query("select distinct notableProjects from NotableProjectsJpaModel notableProjects left join fetch notableProjects.talent where notableProjects.id =:id")
    NotableProjectsJpaModel findOneWithTalent(@Param("id") String id);

    @Query("select notableProjects from NotableProjectsJpaModel notableProjects where notableProjects.id =:id")
    NotableProjectsJpaModel findOneWithEagerRelationships(@Param("id") String id);

}