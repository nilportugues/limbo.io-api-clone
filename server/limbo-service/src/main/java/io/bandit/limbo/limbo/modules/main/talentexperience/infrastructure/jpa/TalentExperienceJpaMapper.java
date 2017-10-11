package io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaModel;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaRepository;

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
@Named("TalentExperienceJpaMapper")
public class TalentExperienceJpaMapper {

    @Inject private TalentExperienceJpaRepository talentExperienceJpaRepository;

    public TalentExperience toDomain(final TalentExperienceJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final TalentExperience talentExperience = new TalentExperience();

                talentExperience.setId(model.getId());
                talentExperience.setYears(model.getYears());

                return talentExperience;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public TalentExperienceJpaModel toModel(final TalentExperience talentExperience) {

        if (Optional.ofNullable(talentExperience).isPresent()) {
            try {
                TalentExperienceJpaModel model = new TalentExperienceJpaModel();
                if (Optional.ofNullable(talentExperience.getId()).isPresent()) {
                    final TalentExperienceJpaModel dbModel = talentExperienceJpaRepository.findOne(talentExperience.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(talentExperience.getId());
                model.setYears(talentExperience.getYears());


                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
//isManyToMany: 
//isManyToOne: 
//isOneToOne: 
//isAlone: 