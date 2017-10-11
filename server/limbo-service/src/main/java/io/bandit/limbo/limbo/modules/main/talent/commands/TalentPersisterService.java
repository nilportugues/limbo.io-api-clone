package io.bandit.limbo.limbo.modules.main.talent.commands;

import io.bandit.limbo.limbo.infrastructure.cqrs.EventBus;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentCreated;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentUpdated;
import io.bandit.limbo.limbo.modules.main.talent.infrastructure.TalentRepository;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.stream.Collectors;

@Named
public class TalentPersisterService {

    private final TalentRepository talentRepository;
    private final EventBus eventBus;

    @Inject
    public TalentPersisterService(final TalentRepository talentRepository, final EventBus eventBus) {
        this.talentRepository = talentRepository;
        this.eventBus = eventBus;
    }

    public Talent persist(final Talent talent) throws Throwable {

        final Talent existing = talentRepository.findOne(talent.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return create(talent);
        }

        return update(talent);
    }

    public Talent create(final Talent talent) throws Throwable {

        talentRepository.save(talent);
        eventBus.dispatch(new TalentCreated(talent));

        return talent;
    }

    public Talent update(final Talent data) throws Throwable {

        final Talent existing = talentRepository.findOne(data.getId());
        if (!Optional.ofNullable(existing).isPresent()){
            return null; //will trigger a not found up in the chain.
        }

        final Talent talent = updateData(data, existing);
        talentRepository.save(talent);

        //Dispatch changed field events.
        final List<DomainEvent> events = talent.pullEvents();
        events.forEach(eventBus::dispatch);

        //Dispatch updated event
        eventBus.dispatch(new TalentUpdated(talent));

        return talent;
    }

   /**
    * Copy new data provided to the existing domain object from the repository.
    *
    * @param data   New values for Talent.
    * @param talent   Talent instance to copy data values to.
    * @return  An updated Talent instance.
    */
    private Talent updateData(final Talent data, final Talent talent) {

        talent.setEmail(data.getEmail());
        talent.setPassword(data.getPassword());


        final TalentProfile talentProfile = data.getTalentProfile();
        if (Optional.ofNullable(talentProfile).isPresent()) {
            if (!Optional.ofNullable(talentProfile.getId()).isPresent()) {
              try {
                    talentProfile.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            talent.setTalentProfile(talentProfile);
        }


        final TalentRole talentRole = data.getTalentRole();
        if (Optional.ofNullable(talentRole).isPresent()) {
            if (!Optional.ofNullable(talentRole.getId()).isPresent()) {
              try {
                    talentRole.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            talent.setTalentRole(talentRole);
        }


        final Country country = data.getCountry();
        if (Optional.ofNullable(country).isPresent()) {
            if (!Optional.ofNullable(country.getId()).isPresent()) {
              try {
                    country.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            talent.setCountry(country);
        }


        final City city = data.getCity();
        if (Optional.ofNullable(city).isPresent()) {
            if (!Optional.ofNullable(city.getId()).isPresent()) {
              try {
                    city.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            talent.setCity(city);
        }


        final TalentTitle talentTitle = data.getTalentTitle();
        if (Optional.ofNullable(talentTitle).isPresent()) {
            if (!Optional.ofNullable(talentTitle.getId()).isPresent()) {
              try {
                    talentTitle.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            talent.setTalentTitle(talentTitle);
        }


        final TalentExperience talentExperience = data.getTalentExperience();
        if (Optional.ofNullable(talentExperience).isPresent()) {
            if (!Optional.ofNullable(talentExperience.getId()).isPresent()) {
              try {
                    talentExperience.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            talent.setTalentExperience(talentExperience);
        }


        final WorkType workType = data.getWorkType();
        if (Optional.ofNullable(workType).isPresent()) {
            if (!Optional.ofNullable(workType.getId()).isPresent()) {
              try {
                    workType.setId(UUID.randomUUID().toString());
                  } catch (Throwable ignored) {}
            }
            talent.setWorkType(workType);
        }


        final Set<Skills> skills = data.getSkills();
        if (Optional.ofNullable(skills).isPresent()) {
            skills.stream()
                .peek(v -> {
                     try {
                         if (!Optional.ofNullable(v.getId()).isPresent()) {
                             v.setId(UUID.randomUUID().toString());
                         }
                     } catch (Throwable ignored) {
                     }
                })
                .collect(Collectors.toCollection(HashSet::new));

            talent.setSkills(skills);
        }

        final Set<NotableProjects> notableProjects = data.getNotableProjects();
        if (Optional.ofNullable(notableProjects).isPresent()) {
            notableProjects.stream()
                .peek(v -> {
                     try {
                         if (!Optional.ofNullable(v.getId()).isPresent()) {
                             v.setId(UUID.randomUUID().toString());
                         }
                     } catch (Throwable ignored) {
                     }
                })
                .collect(Collectors.toCollection(HashSet::new));

            talent.setNotableProjects(notableProjects);
        }

        final Set<CompanyTraits> companyTraits = data.getCompanyTraits();
        if (Optional.ofNullable(companyTraits).isPresent()) {
            companyTraits.stream()
                .peek(v -> {
                     try {
                         if (!Optional.ofNullable(v.getId()).isPresent()) {
                             v.setId(UUID.randomUUID().toString());
                         }
                     } catch (Throwable ignored) {
                     }
                })
                .collect(Collectors.toCollection(HashSet::new));

            talent.setCompanyTraits(companyTraits);
        }

        final Set<PersonalTraits> personalTraits = data.getPersonalTraits();
        if (Optional.ofNullable(personalTraits).isPresent()) {
            personalTraits.stream()
                .peek(v -> {
                     try {
                         if (!Optional.ofNullable(v.getId()).isPresent()) {
                             v.setId(UUID.randomUUID().toString());
                         }
                     } catch (Throwable ignored) {
                     }
                })
                .collect(Collectors.toCollection(HashSet::new));

            talent.setPersonalTraits(personalTraits);
        }

        final Set<SocialNetworks> socialNetworks = data.getSocialNetworks();
        if (Optional.ofNullable(socialNetworks).isPresent()) {
            socialNetworks.stream()
                .peek(v -> {
                     try {
                         if (!Optional.ofNullable(v.getId()).isPresent()) {
                             v.setId(UUID.randomUUID().toString());
                         }
                     } catch (Throwable ignored) {
                     }
                })
                .collect(Collectors.toCollection(HashSet::new));

            talent.setSocialNetworks(socialNetworks);
        }

        final Set<JobOffer> jobOffers = data.getJobOffers();
        if (Optional.ofNullable(jobOffers).isPresent()) {
            jobOffers.stream()
                .peek(v -> {
                     try {
                         if (!Optional.ofNullable(v.getId()).isPresent()) {
                             v.setId(UUID.randomUUID().toString());
                         }
                     } catch (Throwable ignored) {
                     }
                })
                .collect(Collectors.toCollection(HashSet::new));

            talent.setJobOffers(jobOffers);
        }

        return talent;
    }
}
