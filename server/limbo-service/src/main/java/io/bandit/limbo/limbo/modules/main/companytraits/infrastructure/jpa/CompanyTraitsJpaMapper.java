package io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaRepository;
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
@Named("CompanyTraitsJpaMapper")
public class CompanyTraitsJpaMapper {

    @Inject private CompanyTraitsJpaRepository companyTraitsJpaRepository;
    @Inject private TalentJpaRepository talentJpaRepository;
    @Inject private TalentJpaMapper talentJpaMapper;

    public CompanyTraits toDomain(final CompanyTraitsJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final CompanyTraits companyTraits = new CompanyTraits();

                companyTraits.setId(model.getId());
                companyTraits.setTitle(model.getTitle());

                return companyTraits;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public CompanyTraitsJpaModel toModel(final CompanyTraits companyTraits) {

        if (Optional.ofNullable(companyTraits).isPresent()) {
            try {
                CompanyTraitsJpaModel model = new CompanyTraitsJpaModel();
                if (Optional.ofNullable(companyTraits.getId()).isPresent()) {
                    final CompanyTraitsJpaModel dbModel = companyTraitsJpaRepository.findOne(companyTraits.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(companyTraits.getId());
                model.setTitle(companyTraits.getTitle());

                manyToOneTalentRelationship(companyTraits, model);

                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
   /**
    * Sets up the many to one relationship between CompanyTraits and Talent.
    *
    * @param companyTraits  The domain class representation for CompanyTraits.
    * @param model  The model class representation for CompanyTraits.
    */
    private void manyToOneTalentRelationship(final CompanyTraits companyTraits, final CompanyTraitsJpaModel model) {

        if (Optional.ofNullable(companyTraits.getTalent()).isPresent()) {
            final Talent talent = companyTraits.getTalent();
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