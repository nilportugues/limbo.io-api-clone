package io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaModel;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaRepository;
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
@Named("JobOfferJpaMapper")
public class JobOfferJpaMapper {

    @Inject private JobOfferJpaRepository jobOfferJpaRepository;
    @Inject private TalentJpaRepository talentJpaRepository;
    @Inject private TalentJpaMapper talentJpaMapper;

    public JobOffer toDomain(final JobOfferJpaModel model) {

        if (Optional.ofNullable(model).isPresent()) {
            try {
                final JobOffer jobOffer = new JobOffer();

                jobOffer.setId(model.getId());
                jobOffer.setTitle(model.getTitle());
                jobOffer.setDescription(model.getDescription());
                jobOffer.setSalaryMax(model.getSalaryMax());
                jobOffer.setSalaryMin(model.getSalaryMin());
                jobOffer.setSalaryCurrency(model.getSalaryCurrency());

                return jobOffer;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public JobOfferJpaModel toModel(final JobOffer jobOffer) {

        if (Optional.ofNullable(jobOffer).isPresent()) {
            try {
                JobOfferJpaModel model = new JobOfferJpaModel();
                if (Optional.ofNullable(jobOffer.getId()).isPresent()) {
                    final JobOfferJpaModel dbModel = jobOfferJpaRepository.findOne(jobOffer.getId());
                    if (null != dbModel) {
                        model = dbModel;
                    }
                }

                model.setId(jobOffer.getId());
                model.setTitle(jobOffer.getTitle());
                model.setDescription(jobOffer.getDescription());
                model.setSalaryMax(jobOffer.getSalaryMax());
                model.setSalaryMin(jobOffer.getSalaryMin());
                model.setSalaryCurrency(jobOffer.getSalaryCurrency());

                manyToOneTalentRelationship(jobOffer, model);

                return model;

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return null;
    }
   /**
    * Sets up the many to one relationship between JobOffer and Talent.
    *
    * @param jobOffer  The domain class representation for JobOffer.
    * @param model  The model class representation for JobOffer.
    */
    private void manyToOneTalentRelationship(final JobOffer jobOffer, final JobOfferJpaModel model) {

        if (Optional.ofNullable(jobOffer.getTalent()).isPresent()) {
            final Talent talent = jobOffer.getTalent();
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