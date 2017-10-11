package io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaModel;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaRepository;

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
@Named("TalentTitleJpaMapper")
public class TalentTitleJpaMapper {

    @Inject private TalentTitleJpaRepository talentTitleJpaRepository;

    public TalentTitle toDomain(final TalentTitleJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final TalentTitle talentTitle = new TalentTitle();

                talentTitle.setId(model.getId());
                talentTitle.setTitle(model.getTitle());

                return talentTitle;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public TalentTitleJpaModel toModel(final TalentTitle talentTitle) {

        if (Optional.ofNullable(talentTitle).isPresent()) {
            try {
                TalentTitleJpaModel model = new TalentTitleJpaModel();
                if (Optional.ofNullable(talentTitle.getId()).isPresent()) {
                    final TalentTitleJpaModel dbModel = talentTitleJpaRepository.findOne(talentTitle.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(talentTitle.getId());
                model.setTitle(talentTitle.getTitle());


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