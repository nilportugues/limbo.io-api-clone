package io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaModel;
import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaRepository;

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
@Named("TalentProfileJpaMapper")
public class TalentProfileJpaMapper {

    @Inject private TalentProfileJpaRepository talentProfileJpaRepository;

    public TalentProfile toDomain(final TalentProfileJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final TalentProfile talentProfile = new TalentProfile();

                talentProfile.setId(model.getId());
                talentProfile.setFirstName(model.getFirstName());
                talentProfile.setLastName(model.getLastName());

                return talentProfile;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public TalentProfileJpaModel toModel(final TalentProfile talentProfile) {

        if (Optional.ofNullable(talentProfile).isPresent()) {
            try {
                TalentProfileJpaModel model = new TalentProfileJpaModel();
                if (Optional.ofNullable(talentProfile.getId()).isPresent()) {
                    final TalentProfileJpaModel dbModel = talentProfileJpaRepository.findOne(talentProfile.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(talentProfile.getId());
                model.setFirstName(talentProfile.getFirstName());
                model.setLastName(talentProfile.getLastName());


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