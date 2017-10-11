package io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaModel;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaRepository;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaMapper;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaModel;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaRepository;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa.TalentJpaMapper;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.List;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Named("SocialNetworksJpaMapper")
public class SocialNetworksJpaMapper {

    @Inject private SocialNetworksJpaRepository socialNetworksJpaRepository;
    @Inject private TalentJpaRepository talentJpaRepository;
    @Inject private TalentJpaMapper talentJpaMapper;

    public SocialNetworks toDomain(final SocialNetworksJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final SocialNetworks socialNetworks = new SocialNetworks();

                socialNetworks.setId(model.getId());
                socialNetworks.setName(model.getName());
                socialNetworks.setUrl(model.getUrl());

                return socialNetworks;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public SocialNetworksJpaModel toModel(final SocialNetworks socialNetworks) {

        if (Optional.ofNullable(socialNetworks).isPresent()) {
            try {
                SocialNetworksJpaModel model = new SocialNetworksJpaModel();
                if (Optional.ofNullable(socialNetworks.getId()).isPresent()) {
                    final SocialNetworksJpaModel dbModel = socialNetworksJpaRepository.findOne(socialNetworks.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(socialNetworks.getId());
                model.setName(socialNetworks.getName());
                model.setUrl(socialNetworks.getUrl());

                manyToOneTalentRelationship(socialNetworks, model);

                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
   /**
    * Sets up the many to one relationship between SocialNetworks and Talent.
    *
    * @param socialNetworks  The domain class representation for SocialNetworks.
    * @param model  The model class representation for SocialNetworks.
    */
    private void manyToOneTalentRelationship(final SocialNetworks socialNetworks, final SocialNetworksJpaModel model) {

        if (Optional.ofNullable(socialNetworks.getTalent()).isPresent()) {
            final Talent talent = socialNetworks.getTalent();
            final String id = talent.getId();

            TalentJpaModel talentModel = null;
            if (Optional.ofNullable(talent.getId()).isPresent()) {
                talentModel = talentJpaRepository.findOne(id);
            }

            if (Objects.equals(model.getTalent(), talentModel)
                && Optional.ofNullable(model.getTalent()).isPresent()) {
                return;
            }

            if (!Optional.ofNullable(talentModel).isPresent()) {
                talentModel = talentJpaMapper.toModel(talent);
            }

            if (null != model.getTalent()) {
                talentJpaRepository.delete(model.getTalent());
            }

            model.setTalent(talentModel);
        }
    }

}
//isManyToMany: 
//isManyToOne: true
//isOneToOne: 
//isAlone: 