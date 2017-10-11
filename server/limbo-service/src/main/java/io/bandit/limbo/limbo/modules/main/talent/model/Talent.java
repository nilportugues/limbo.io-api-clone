package io.bandit.limbo.limbo.modules.main.talent.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talentprofile.model.TalentProfile;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.talentrole.model.TalentRole;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.country.model.Country;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.city.model.City;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.talenttitle.model.TalentTitle;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.talentexperience.model.TalentExperience;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.worktype.model.WorkType;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.skills.model.Skills;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.notableprojects.model.NotableProjects;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.companytraits.model.CompanyTraits;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.socialnetworks.model.SocialNetworks;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.joboffer.model.JobOffer;
import io.bandit.limbo.limbo.modules.main.talent.event.*;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentEmailChanged;
import io.bandit.limbo.limbo.modules.main.talent.event.TalentPasswordChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A Talent Domain Entity.
 */
public class Talent extends Aggregate {

    private String id;

    private String email;
    private String password;
    private TalentProfile talentProfile;
    private TalentRole talentRole;
    private Country country;
    private City city;
    private TalentTitle talentTitle;
    private TalentExperience talentExperience;
    private WorkType workType;
    private Set<Skills> skills = new HashSet<>();
    private Set<NotableProjects> notableProjects = new HashSet<>();
    private Set<CompanyTraits> companyTraits = new HashSet<>();
    private Set<PersonalTraits> personalTraits = new HashSet<>();
    private Set<SocialNetworks> socialNetworks = new HashSet<>();
    private Set<JobOffer> jobOffers = new HashSet<>();

    public static Talent create(String id, String email, String password) {
        final Talent self = new Talent();
        self.id = id;
        self.email = email;
        self.password = password;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new TalentImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {

        if (null != email && !email.equals(this.email)) {
            this.email = email;
            apply(new TalentEmailChanged(this));
        }
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        if (null != password && !password.equals(this.password)) {
            this.password = password;
            apply(new TalentPasswordChanged(this));
        }
    }
    public TalentProfile getTalentProfile() {
        return talentProfile;
    }

    public void setTalentProfile(TalentProfile talentProfile) {
        if (!Objects.equals(this.talentProfile, talentProfile)) {
            removeTalentProfile();
            this.talentProfile = talentProfile;
            apply(new TalentTalentProfileChanged(this));
        }
    }

    public void removeTalentProfile() {
        if (null != this.talentProfile) {
            apply(new TalentTalentProfileChanged(this));
            this.talentProfile = null;
        }
    }

    public TalentRole getTalentRole() {
        return talentRole;
    }

    public void setTalentRole(TalentRole talentRole) {
        if (!Objects.equals(this.talentRole, talentRole)) {
            removeTalentRole();
            this.talentRole = talentRole;
            apply(new TalentTalentRoleChanged(this));
        }
    }

    public void removeTalentRole() {
        if (null != this.talentRole) {
            apply(new TalentTalentRoleChanged(this));
            this.talentRole = null;
        }
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        if (!Objects.equals(this.country, country)) {
            removeCountry();
            this.country = country;
            apply(new TalentCountryChanged(this));
        }
    }

    public void removeCountry() {
        if (null != this.country) {
            apply(new TalentCountryChanged(this));
            this.country = null;
        }
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        if (!Objects.equals(this.city, city)) {
            removeCity();
            this.city = city;
            apply(new TalentCityChanged(this));
        }
    }

    public void removeCity() {
        if (null != this.city) {
            apply(new TalentCityChanged(this));
            this.city = null;
        }
    }

    public TalentTitle getTalentTitle() {
        return talentTitle;
    }

    public void setTalentTitle(TalentTitle talentTitle) {
        if (!Objects.equals(this.talentTitle, talentTitle)) {
            removeTalentTitle();
            this.talentTitle = talentTitle;
            apply(new TalentTalentTitleChanged(this));
        }
    }

    public void removeTalentTitle() {
        if (null != this.talentTitle) {
            apply(new TalentTalentTitleChanged(this));
            this.talentTitle = null;
        }
    }

    public TalentExperience getTalentExperience() {
        return talentExperience;
    }

    public void setTalentExperience(TalentExperience talentExperience) {
        if (!Objects.equals(this.talentExperience, talentExperience)) {
            removeTalentExperience();
            this.talentExperience = talentExperience;
            apply(new TalentTalentExperienceChanged(this));
        }
    }

    public void removeTalentExperience() {
        if (null != this.talentExperience) {
            apply(new TalentTalentExperienceChanged(this));
            this.talentExperience = null;
        }
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        if (!Objects.equals(this.workType, workType)) {
            removeWorkType();
            this.workType = workType;
            apply(new TalentWorkTypeChanged(this));
        }
    }

    public void removeWorkType() {
        if (null != this.workType) {
            apply(new TalentWorkTypeChanged(this));
            this.workType = null;
        }
    }


    public Set<Skills> getSkills() {
        return skills;
    }

    public void addSkills(Skills skills) {
        if (!Optional.ofNullable(this.skills).isPresent()) {
            this.skills = new HashSet<>();
        }

        this.skills.add(skills);
        skills.setTalent(this);
        apply(new  TalentSkillsAdded(this, skills));
    }

    public void removeSkills(Skills skills) {
        if (Optional.ofNullable(this.skills).isPresent() && this.skills.contains(skills)) {
            this.skills.remove(skills);
            apply(new TalentSkillsRemoved(this, skills));
        }
    }

    public void setSkills(Set<Skills> skills) {

        Set<Skills> input = skills;
        if (!Optional.ofNullable(skills).isPresent()) {
            input = new HashSet<>();
        }

        if (Objects.equals(this.skills, null)) {
            this.skills = new HashSet<>();
        }

        if (!Objects.equals(this.skills, input)) {
            this.skills.forEach(v -> apply(new TalentSkillsRemoved(this, v)));

            this.skills.clear();
            this.skills.addAll(input);

            this.skills.forEach(v -> apply(new TalentSkillsAdded(this, v)));
        }
    }

    public Set<NotableProjects> getNotableProjects() {
        return notableProjects;
    }

    public void addNotableProjects(NotableProjects notableProjects) {
        if (!Optional.ofNullable(this.notableProjects).isPresent()) {
            this.notableProjects = new HashSet<>();
        }

        this.notableProjects.add(notableProjects);
        notableProjects.setTalent(this);
        apply(new  TalentNotableProjectsAdded(this, notableProjects));
    }

    public void removeNotableProjects(NotableProjects notableProjects) {
        if (Optional.ofNullable(this.notableProjects).isPresent() && this.notableProjects.contains(notableProjects)) {
            this.notableProjects.remove(notableProjects);
            apply(new TalentNotableProjectsRemoved(this, notableProjects));
        }
    }

    public void setNotableProjects(Set<NotableProjects> notableProjects) {

        Set<NotableProjects> input = notableProjects;
        if (!Optional.ofNullable(notableProjects).isPresent()) {
            input = new HashSet<>();
        }

        if (Objects.equals(this.notableProjects, null)) {
            this.notableProjects = new HashSet<>();
        }

        if (!Objects.equals(this.notableProjects, input)) {
            this.notableProjects.forEach(v -> apply(new TalentNotableProjectsRemoved(this, v)));

            this.notableProjects.clear();
            this.notableProjects.addAll(input);

            this.notableProjects.forEach(v -> apply(new TalentNotableProjectsAdded(this, v)));
        }
    }

    public Set<CompanyTraits> getCompanyTraits() {
        return companyTraits;
    }

    public void addCompanyTraits(CompanyTraits companyTraits) {
        if (!Optional.ofNullable(this.companyTraits).isPresent()) {
            this.companyTraits = new HashSet<>();
        }

        this.companyTraits.add(companyTraits);
        companyTraits.setTalent(this);
        apply(new  TalentCompanyTraitsAdded(this, companyTraits));
    }

    public void removeCompanyTraits(CompanyTraits companyTraits) {
        if (Optional.ofNullable(this.companyTraits).isPresent() && this.companyTraits.contains(companyTraits)) {
            this.companyTraits.remove(companyTraits);
            apply(new TalentCompanyTraitsRemoved(this, companyTraits));
        }
    }

    public void setCompanyTraits(Set<CompanyTraits> companyTraits) {

        Set<CompanyTraits> input = companyTraits;
        if (!Optional.ofNullable(companyTraits).isPresent()) {
            input = new HashSet<>();
        }

        if (Objects.equals(this.companyTraits, null)) {
            this.companyTraits = new HashSet<>();
        }

        if (!Objects.equals(this.companyTraits, input)) {
            this.companyTraits.forEach(v -> apply(new TalentCompanyTraitsRemoved(this, v)));

            this.companyTraits.clear();
            this.companyTraits.addAll(input);

            this.companyTraits.forEach(v -> apply(new TalentCompanyTraitsAdded(this, v)));
        }
    }

    public Set<PersonalTraits> getPersonalTraits() {
        return personalTraits;
    }

    public void addPersonalTraits(PersonalTraits personalTraits) {
        if (!Optional.ofNullable(this.personalTraits).isPresent()) {
            this.personalTraits = new HashSet<>();
        }

        this.personalTraits.add(personalTraits);
        personalTraits.setTalent(this);
        apply(new  TalentPersonalTraitsAdded(this, personalTraits));
    }

    public void removePersonalTraits(PersonalTraits personalTraits) {
        if (Optional.ofNullable(this.personalTraits).isPresent() && this.personalTraits.contains(personalTraits)) {
            this.personalTraits.remove(personalTraits);
            apply(new TalentPersonalTraitsRemoved(this, personalTraits));
        }
    }

    public void setPersonalTraits(Set<PersonalTraits> personalTraits) {

        Set<PersonalTraits> input = personalTraits;
        if (!Optional.ofNullable(personalTraits).isPresent()) {
            input = new HashSet<>();
        }

        if (Objects.equals(this.personalTraits, null)) {
            this.personalTraits = new HashSet<>();
        }

        if (!Objects.equals(this.personalTraits, input)) {
            this.personalTraits.forEach(v -> apply(new TalentPersonalTraitsRemoved(this, v)));

            this.personalTraits.clear();
            this.personalTraits.addAll(input);

            this.personalTraits.forEach(v -> apply(new TalentPersonalTraitsAdded(this, v)));
        }
    }

    public Set<SocialNetworks> getSocialNetworks() {
        return socialNetworks;
    }

    public void addSocialNetworks(SocialNetworks socialNetworks) {
        if (!Optional.ofNullable(this.socialNetworks).isPresent()) {
            this.socialNetworks = new HashSet<>();
        }

        this.socialNetworks.add(socialNetworks);
        socialNetworks.setTalent(this);
        apply(new  TalentSocialNetworksAdded(this, socialNetworks));
    }

    public void removeSocialNetworks(SocialNetworks socialNetworks) {
        if (Optional.ofNullable(this.socialNetworks).isPresent() && this.socialNetworks.contains(socialNetworks)) {
            this.socialNetworks.remove(socialNetworks);
            apply(new TalentSocialNetworksRemoved(this, socialNetworks));
        }
    }

    public void setSocialNetworks(Set<SocialNetworks> socialNetworks) {

        Set<SocialNetworks> input = socialNetworks;
        if (!Optional.ofNullable(socialNetworks).isPresent()) {
            input = new HashSet<>();
        }

        if (Objects.equals(this.socialNetworks, null)) {
            this.socialNetworks = new HashSet<>();
        }

        if (!Objects.equals(this.socialNetworks, input)) {
            this.socialNetworks.forEach(v -> apply(new TalentSocialNetworksRemoved(this, v)));

            this.socialNetworks.clear();
            this.socialNetworks.addAll(input);

            this.socialNetworks.forEach(v -> apply(new TalentSocialNetworksAdded(this, v)));
        }
    }

    public Set<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void addJobOffer(JobOffer jobOffer) {
        if (!Optional.ofNullable(this.jobOffers).isPresent()) {
            this.jobOffers = new HashSet<>();
        }

        this.jobOffers.add(jobOffer);
        jobOffer.setTalent(this);
        apply(new  TalentJobOfferAdded(this, jobOffer));
    }

    public void removeJobOffer(JobOffer jobOffer) {
        if (Optional.ofNullable(this.jobOffers).isPresent() && this.jobOffers.contains(jobOffer)) {
            this.jobOffers.remove(jobOffer);
            apply(new TalentJobOfferRemoved(this, jobOffer));
        }
    }

    public void setJobOffers(Set<JobOffer> jobOffers) {

        Set<JobOffer> input = jobOffers;
        if (!Optional.ofNullable(jobOffers).isPresent()) {
            input = new HashSet<>();
        }

        if (Objects.equals(this.jobOffers, null)) {
            this.jobOffers = new HashSet<>();
        }

        if (!Objects.equals(this.jobOffers, input)) {
            this.jobOffers.forEach(v -> apply(new TalentJobOfferRemoved(this, v)));

            this.jobOffers.clear();
            this.jobOffers.addAll(input);

            this.jobOffers.forEach(v -> apply(new TalentJobOfferAdded(this, v)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Talent talent = (Talent) o;

        return !(talent.id == null || id == null) && Objects.equals(id, talent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Talent{" +
            "id=" + id +
            ", email='" + email + "'" +
            ", password='" + password + "'" +
            '}';
    }
}
