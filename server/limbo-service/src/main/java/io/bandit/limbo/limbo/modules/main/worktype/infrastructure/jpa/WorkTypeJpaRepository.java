package io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the WorkTypeJpaModel entity.
 */
@Named("WorkTypeJpaRepository")
@SuppressWarnings("unused")
public interface WorkTypeJpaRepository extends JpaRepository<WorkTypeJpaModel,String>, JpaSpecificationExecutor {


    @Query("select workType from WorkTypeJpaModel workType where workType.id =:id")
    WorkTypeJpaModel findOneWithEagerRelationships(@Param("id") String id);

}