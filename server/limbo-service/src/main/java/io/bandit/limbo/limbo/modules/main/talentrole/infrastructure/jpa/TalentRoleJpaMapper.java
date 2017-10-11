package io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaModel;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaRepository;

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
@Named("TalentRoleJpaMapper")
public class TalentRoleJpaMapper {

    @Inject private TalentRoleJpaRepository talentRoleJpaRepository;

    public TalentRole toDomain(final TalentRoleJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final TalentRole talentRole = new TalentRole();

                talentRole.setId(model.getId());
                talentRole.setTitle(model.getTitle());
                talentRole.setDescription(model.getDescription());

                return talentRole;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public TalentRoleJpaModel toModel(final TalentRole talentRole) {

        if (Optional.ofNullable(talentRole).isPresent()) {
            try {
                TalentRoleJpaModel model = new TalentRoleJpaModel();
                if (Optional.ofNullable(talentRole.getId()).isPresent()) {
                    final TalentRoleJpaModel dbModel = talentRoleJpaRepository.findOne(talentRole.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(talentRole.getId());
                model.setTitle(talentRole.getTitle());
                model.setDescription(talentRole.getDescription());


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