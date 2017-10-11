package io.bandit.limbo.limbo.modules.main.talent.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.shared.events.DomainEvent;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class TalentCreated extends DomainEvent<Talent> {

    @JsonIgnore
    private static final Integer EVENT_VERSION = 1;

    @JsonIgnore
    public static final String EVENT_NAME = "talent.created";

    public TalentCreated(final Talent talent) {
        setVersion(EVENT_VERSION);
        setType(EVENT_NAME);
        setPayload(buildPayload(talent));
    }

    private Payload buildPayload(final Talent talent) {

        if (!Optional.ofNullable(talent.getId()).isPresent()) {
            return null;
        }

        String talentProfileId = null;
        if (Optional.ofNullable(talent.getTalentProfile()).isPresent()) {
            talentProfileId = talent.getTalentProfile().getId();
        }

        String talentRoleId = null;
        if (Optional.ofNullable(talent.getTalentRole()).isPresent()) {
            talentRoleId = talent.getTalentRole().getId();
        }

        String countryId = null;
        if (Optional.ofNullable(talent.getCountry()).isPresent()) {
            countryId = talent.getCountry().getId();
        }

        String cityId = null;
        if (Optional.ofNullable(talent.getCity()).isPresent()) {
            cityId = talent.getCity().getId();
        }

        String talentTitleId = null;
        if (Optional.ofNullable(talent.getTalentTitle()).isPresent()) {
            talentTitleId = talent.getTalentTitle().getId();
        }

        String talentExperienceId = null;
        if (Optional.ofNullable(talent.getTalentExperience()).isPresent()) {
            talentExperienceId = talent.getTalentExperience().getId();
        }

        String workTypeId = null;
        if (Optional.ofNullable(talent.getWorkType()).isPresent()) {
            workTypeId = talent.getWorkType().getId();
        }

        final Set<String> skillsIds = new HashSet<>();
        if (Optional.ofNullable(talent.getSkills()).isPresent()) {
            talent.getSkills().forEach(skills -> skillsIds.add(skills.getId()));
        }

        final Set<String> notableProjectsIds = new HashSet<>();
        if (Optional.ofNullable(talent.getNotableProjects()).isPresent()) {
            talent.getNotableProjects().forEach(notableProjects -> notableProjectsIds.add(notableProjects.getId()));
        }

        final Set<String> companyTraitsIds = new HashSet<>();
        if (Optional.ofNullable(talent.getCompanyTraits()).isPresent()) {
            talent.getCompanyTraits().forEach(companyTraits -> companyTraitsIds.add(companyTraits.getId()));
        }

        final Set<String> personalTraitsIds = new HashSet<>();
        if (Optional.ofNullable(talent.getPersonalTraits()).isPresent()) {
            talent.getPersonalTraits().forEach(personalTraits -> personalTraitsIds.add(personalTraits.getId()));
        }

        final Set<String> socialNetworksIds = new HashSet<>();
        if (Optional.ofNullable(talent.getSocialNetworks()).isPresent()) {
            talent.getSocialNetworks().forEach(socialNetworks -> socialNetworksIds.add(socialNetworks.getId()));
        }

        final Set<String> jobOffersIds = new HashSet<>();
        if (Optional.ofNullable(talent.getJobOffers()).isPresent()) {
            talent.getJobOffers().forEach(jobOffer -> jobOffersIds.add(jobOffer.getId()));
        }


        return new Payload(talent.getId(), talent.getEmail(), talent.getPassword(), talentProfileId, talentRoleId, countryId, cityId, talentTitleId, talentExperienceId, workTypeId, skillsIds, notableProjectsIds, companyTraitsIds, personalTraitsIds, socialNetworksIds, jobOffersIds);
    }

    @Override
    public Payload getPayload() {
        return (Payload) payload;
    }

    public class Payload implements DomainEvent.Payload {

        @JsonProperty(value = "id")
        private String talentId;

        @JsonProperty(value = "type")
        private final String type = "talent";

        @JsonProperty(value = "attributes")
        private Attributes attributes;

        public Payload(final String talentId, final String email, final String password, final String talentProfileId, final String talentRoleId, final String countryId, final String cityId, final String talentTitleId, final String talentExperienceId, final String workTypeId, final Set<String> skillsIds, final Set<String> notableProjectsIds, final Set<String> companyTraitsIds, final Set<String> personalTraitsIds, final Set<String> socialNetworksIds, final Set<String> jobOffersIds) {
            this.talentId = talentId;
            this.attributes = new Attributes(email, password, talentProfileId, talentRoleId, countryId, cityId, talentTitleId, talentExperienceId, workTypeId, skillsIds, notableProjectsIds, companyTraitsIds, personalTraitsIds, socialNetworksIds, jobOffersIds);
        }

        @Override
        public String getId() {
            return talentId;
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public Attributes getAttributes() {
            return attributes;
        }
    }

    public class Attributes implements DomainEvent.Attributes {

        @JsonProperty(value = "email")
        private final String email;

        @JsonProperty(value = "password")
        private final String password;

        @JsonProperty(value = "talent_profile")
        private final String talentProfileId;

        @JsonProperty(value = "talent_role")
        private final String talentRoleId;

        @JsonProperty(value = "country")
        private final String countryId;

        @JsonProperty(value = "city")
        private final String cityId;

        @JsonProperty(value = "talent_title")
        private final String talentTitleId;

        @JsonProperty(value = "talent_experience")
        private final String talentExperienceId;

        @JsonProperty(value = "work_type")
        private final String workTypeId;

        @JsonProperty(value = "skills")
        private final Set<String> skillsIds;

        @JsonProperty(value = "notable_projects")
        private final Set<String> notableProjectsIds;

        @JsonProperty(value = "company_traits")
        private final Set<String> companyTraitsIds;

        @JsonProperty(value = "personal_traits")
        private final Set<String> personalTraitsIds;

        @JsonProperty(value = "social_networks")
        private final Set<String> socialNetworksIds;

        @JsonProperty(value = "job_offers")
        private final Set<String> jobOffersIds;

        public Attributes(final String email, final String password, final String talentProfileId, final String talentRoleId, final String countryId, final String cityId, final String talentTitleId, final String talentExperienceId, final String workTypeId, final Set<String> skillsIds, final Set<String> notableProjectsIds, final Set<String> companyTraitsIds, final Set<String> personalTraitsIds, final Set<String> socialNetworksIds, final Set<String> jobOffersIds) {

            this.email = email;
            this.password = password;
            this.talentProfileId = talentProfileId;
            this.talentRoleId = talentRoleId;
            this.countryId = countryId;
            this.cityId = cityId;
            this.talentTitleId = talentTitleId;
            this.talentExperienceId = talentExperienceId;
            this.workTypeId = workTypeId;
            this.skillsIds = skillsIds;
            this.notableProjectsIds = notableProjectsIds;
            this.companyTraitsIds = companyTraitsIds;
            this.personalTraitsIds = personalTraitsIds;
            this.socialNetworksIds = socialNetworksIds;
            this.jobOffersIds = jobOffersIds;
        }

        @JsonIgnore
        public String getEmail() {
            return email;
        }
        @JsonIgnore
        public String getPassword() {
            return password;
        }

        @JsonIgnore
        public String getTalentProfile() {
            return talentProfileId;
        }

        @JsonIgnore
        public String getTalentRole() {
            return talentRoleId;
        }

        @JsonIgnore
        public String getCountry() {
            return countryId;
        }

        @JsonIgnore
        public String getCity() {
            return cityId;
        }

        @JsonIgnore
        public String getTalentTitle() {
            return talentTitleId;
        }

        @JsonIgnore
        public String getTalentExperience() {
            return talentExperienceId;
        }

        @JsonIgnore
        public String getWorkType() {
            return workTypeId;
        }

        @JsonIgnore
        public Set<String> getSkills() {
            return skillsIds;
        }

        @JsonIgnore
        public Set<String> getNotableProjects() {
            return notableProjectsIds;
        }

        @JsonIgnore
        public Set<String> getCompanyTraits() {
            return companyTraitsIds;
        }

        @JsonIgnore
        public Set<String> getPersonalTraits() {
            return personalTraitsIds;
        }

        @JsonIgnore
        public Set<String> getSocialNetworks() {
            return socialNetworksIds;
        }

        @JsonIgnore
        public Set<String> getJobOffers() {
            return jobOffersIds;
        }
    }
}
