package io.bandit.limbo.limbo.modules.main.talent.infrastructure.jpa;

import io.bandit.limbo.limbo.modules.main.talentprofile.infrastructure.jpa.TalentProfileJpaModel;
import io.bandit.limbo.limbo.modules.main.talentrole.infrastructure.jpa.TalentRoleJpaModel;
import io.bandit.limbo.limbo.modules.main.country.infrastructure.jpa.CountryJpaModel;
import io.bandit.limbo.limbo.modules.main.city.infrastructure.jpa.CityJpaModel;
import io.bandit.limbo.limbo.modules.main.talenttitle.infrastructure.jpa.TalentTitleJpaModel;
import io.bandit.limbo.limbo.modules.main.talentexperience.infrastructure.jpa.TalentExperienceJpaModel;
import io.bandit.limbo.limbo.modules.main.worktype.infrastructure.jpa.WorkTypeJpaModel;
import io.bandit.limbo.limbo.modules.main.skills.infrastructure.jpa.SkillsJpaModel;
import io.bandit.limbo.limbo.modules.main.notableprojects.infrastructure.jpa.NotableProjectsJpaModel;
import io.bandit.limbo.limbo.modules.main.companytraits.infrastructure.jpa.CompanyTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.personaltraits.infrastructure.jpa.PersonalTraitsJpaModel;
import io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.jpa.SocialNetworksJpaModel;
import io.bandit.limbo.limbo.modules.main.joboffer.infrastructure.jpa.JobOfferJpaModel;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Talent Model.
 */
@Entity
@Table(name = "talent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalentJpaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(unique = true)
    private TalentProfileJpaModel talentProfile;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(unique = true)
    private TalentRoleJpaModel talentRole;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(unique = true)
    private CountryJpaModel country;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(unique = true)
    private CityJpaModel city;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(unique = true)
    private TalentTitleJpaModel talentTitle;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(unique = true)
    private TalentExperienceJpaModel talentExperience;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(unique = true)
    private WorkTypeJpaModel workType;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "talent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SkillsJpaModel> skills = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "talent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NotableProjectsJpaModel> notableProjects = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "talent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CompanyTraitsJpaModel> companyTraits = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "talent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PersonalTraitsJpaModel> personalTraits = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "talent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SocialNetworksJpaModel> socialNetworks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "talent")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<JobOfferJpaModel> jobOffers = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public TalentProfileJpaModel getTalentProfile() {
        return talentProfile;
    }

    public void setTalentProfile(TalentProfileJpaModel talentProfile) {
        this.talentProfile = talentProfile;
    }

    public TalentRoleJpaModel getTalentRole() {
        return talentRole;
    }

    public void setTalentRole(TalentRoleJpaModel talentRole) {
        this.talentRole = talentRole;
    }

    public CountryJpaModel getCountry() {
        return country;
    }

    public void setCountry(CountryJpaModel country) {
        this.country = country;
    }

    public CityJpaModel getCity() {
        return city;
    }

    public void setCity(CityJpaModel city) {
        this.city = city;
    }

    public TalentTitleJpaModel getTalentTitle() {
        return talentTitle;
    }

    public void setTalentTitle(TalentTitleJpaModel talentTitle) {
        this.talentTitle = talentTitle;
    }

    public TalentExperienceJpaModel getTalentExperience() {
        return talentExperience;
    }

    public void setTalentExperience(TalentExperienceJpaModel talentExperience) {
        this.talentExperience = talentExperience;
    }

    public WorkTypeJpaModel getWorkType() {
        return workType;
    }

    public void setWorkType(WorkTypeJpaModel workType) {
        this.workType = workType;
    }


    public Set<SkillsJpaModel> getSkills() {
        return skills;
    }

    public void addSkills(SkillsJpaModel skills) {
        this.skills.add(skills);
        skills.setTalent(this);
    }

    public void removeSkills(SkillsJpaModel skills) {
        this.skills.remove(skills);
        skills.setTalent(null);
    }

    public void setSkills(Set<SkillsJpaModel> skills) {
        this.skills = skills;
    }


    public Set<NotableProjectsJpaModel> getNotableProjects() {
        return notableProjects;
    }

    public void addNotableProjects(NotableProjectsJpaModel notableProjects) {
        this.notableProjects.add(notableProjects);
        notableProjects.setTalent(this);
    }

    public void removeNotableProjects(NotableProjectsJpaModel notableProjects) {
        this.notableProjects.remove(notableProjects);
        notableProjects.setTalent(null);
    }

    public void setNotableProjects(Set<NotableProjectsJpaModel> notableProjects) {
        this.notableProjects = notableProjects;
    }


    public Set<CompanyTraitsJpaModel> getCompanyTraits() {
        return companyTraits;
    }

    public void addCompanyTraits(CompanyTraitsJpaModel companyTraits) {
        this.companyTraits.add(companyTraits);
        companyTraits.setTalent(this);
    }

    public void removeCompanyTraits(CompanyTraitsJpaModel companyTraits) {
        this.companyTraits.remove(companyTraits);
        companyTraits.setTalent(null);
    }

    public void setCompanyTraits(Set<CompanyTraitsJpaModel> companyTraits) {
        this.companyTraits = companyTraits;
    }


    public Set<PersonalTraitsJpaModel> getPersonalTraits() {
        return personalTraits;
    }

    public void addPersonalTraits(PersonalTraitsJpaModel personalTraits) {
        this.personalTraits.add(personalTraits);
        personalTraits.setTalent(this);
    }

    public void removePersonalTraits(PersonalTraitsJpaModel personalTraits) {
        this.personalTraits.remove(personalTraits);
        personalTraits.setTalent(null);
    }

    public void setPersonalTraits(Set<PersonalTraitsJpaModel> personalTraits) {
        this.personalTraits = personalTraits;
    }


    public Set<SocialNetworksJpaModel> getSocialNetworks() {
        return socialNetworks;
    }

    public void addSocialNetworks(SocialNetworksJpaModel socialNetworks) {
        this.socialNetworks.add(socialNetworks);
        socialNetworks.setTalent(this);
    }

    public void removeSocialNetworks(SocialNetworksJpaModel socialNetworks) {
        this.socialNetworks.remove(socialNetworks);
        socialNetworks.setTalent(null);
    }

    public void setSocialNetworks(Set<SocialNetworksJpaModel> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }


    public Set<JobOfferJpaModel> getJobOffers() {
        return jobOffers;
    }

    public void addJobOffer(JobOfferJpaModel jobOffer) {
        this.jobOffers.add(jobOffer);
        jobOffer.setTalent(this);
    }

    public void removeJobOffer(JobOfferJpaModel jobOffer) {
        this.jobOffers.remove(jobOffer);
        jobOffer.setTalent(null);
    }

    public void setJobOffers(Set<JobOfferJpaModel> jobOffers) {
        this.jobOffers = jobOffers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TalentJpaModel talent = (TalentJpaModel) o;

        return !(talent.id == null || id == null) && Objects.equals(id, talent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentJpaModel{" +
            "id=" + id +
            ", email='" + email + "'" +
            ", password='" + password + "'" +
            '}';
    }
}
