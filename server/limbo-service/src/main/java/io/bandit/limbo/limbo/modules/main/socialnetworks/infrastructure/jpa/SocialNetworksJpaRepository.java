package io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;
import javax.inject.Named;

/**
 * Spring Data JPA repository for the SocialNetworksJpaModel entity.
 */
@Named("SocialNetworksJpaRepository")
@SuppressWarnings("unused")
public interface SocialNetworksJpaRepository extends JpaRepository<SocialNetworksJpaModel,String>, JpaSpecificationExecutor {


    @Query("select distinct socialNetworks from SocialNetworksJpaModel socialNetworks left join fetch socialNetworks.talent where socialNetworks.id =:id")
    SocialNetworksJpaModel findOneWithTalent(@Param("id") String id);

    @Query("select socialNetworks from SocialNetworksJpaModel socialNetworks where socialNetworks.id =:id")
    SocialNetworksJpaModel findOneWithEagerRelationships(@Param("id") String id);

}