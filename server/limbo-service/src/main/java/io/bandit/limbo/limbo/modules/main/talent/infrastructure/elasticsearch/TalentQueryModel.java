package io.bandit.limbo.limbo.modules.main.talent.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Talent Read Model.
 */
@Document(indexName = "v1_talent", type = "talent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TalentQueryModel implements Serializable {

    //we should embed[one-to-one]: talentProfileQueryModel;
    //we should embed[one-to-one]: talentRoleQueryModel;
    //we should embed[one-to-one]: countryQueryModel;
    //we should embed[one-to-one]: cityQueryModel;
    //we should embed[one-to-one]: talentTitleQueryModel;
    //we should embed[one-to-one]: talentExperienceQueryModel;
    //we should embed[one-to-one]: workTypeQueryModel;


    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "talent_profile")
    private String talentProfileId;

    @JsonProperty(value = "talent_role")
    private String talentRoleId;

    @JsonProperty(value = "country")
    private String countryId;

    @JsonProperty(value = "city")
    private String cityId;

    @JsonProperty(value = "talent_title")
    private String talentTitleId;

    @JsonProperty(value = "talent_experience")
    private String talentExperienceId;

    @JsonProperty(value = "work_type")
    private String workTypeId;

    @JsonProperty(value = "skills")
    private Set<String> skillsIds = new HashSet<>();

    @JsonProperty(value = "notable_projects")
    private Set<String> notableProjectsIds = new HashSet<>();

    @JsonProperty(value = "company_traits")
    private Set<String> companyTraitsIds = new HashSet<>();

    @JsonProperty(value = "personal_traits")
    private Set<String> personalTraitsIds = new HashSet<>();

    @JsonProperty(value = "social_networks")
    private Set<String> socialNetworksIds = new HashSet<>();

    @JsonProperty(value = "job_offers")
    private Set<String> jobOffersIds = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    @JsonIgnore
    public String getTalentProfile() {
        return talentProfileId;
    }

    public void setTalentProfile(String talentProfileId) {
        this.talentProfileId = talentProfileId;
    }
    @JsonIgnore
    public String getTalentRole() {
        return talentRoleId;
    }

    public void setTalentRole(String talentRoleId) {
        this.talentRoleId = talentRoleId;
    }
    @JsonIgnore
    public String getCountry() {
        return countryId;
    }

    public void setCountry(String countryId) {
        this.countryId = countryId;
    }
    @JsonIgnore
    public String getCity() {
        return cityId;
    }

    public void setCity(String cityId) {
        this.cityId = cityId;
    }
    @JsonIgnore
    public String getTalentTitle() {
        return talentTitleId;
    }

    public void setTalentTitle(String talentTitleId) {
        this.talentTitleId = talentTitleId;
    }
    @JsonIgnore
    public String getTalentExperience() {
        return talentExperienceId;
    }

    public void setTalentExperience(String talentExperienceId) {
        this.talentExperienceId = talentExperienceId;
    }
    @JsonIgnore
    public String getWorkType() {
        return workTypeId;
    }

    public void setWorkType(String workTypeId) {
        this.workTypeId = workTypeId;
    }

    @JsonIgnore
    public Set<String> getSkills() {
        return skillsIds;
    }

    public void addSkills(String skillsId) {
        this.skillsIds.add(skillsId);
    }

    public void removeSkills(String skillsId) {
        this.skillsIds.remove(skillsId);
    }

    public void setSkills(Set<String> skillsIds) {
        this.skillsIds = skillsIds;
    }

    @JsonIgnore
    public Set<String> getNotableProjects() {
        return notableProjectsIds;
    }

    public void addNotableProjects(String notableProjectsId) {
        this.notableProjectsIds.add(notableProjectsId);
    }

    public void removeNotableProjects(String notableProjectsId) {
        this.notableProjectsIds.remove(notableProjectsId);
    }

    public void setNotableProjects(Set<String> notableProjectsIds) {
        this.notableProjectsIds = notableProjectsIds;
    }

    @JsonIgnore
    public Set<String> getCompanyTraits() {
        return companyTraitsIds;
    }

    public void addCompanyTraits(String companyTraitsId) {
        this.companyTraitsIds.add(companyTraitsId);
    }

    public void removeCompanyTraits(String companyTraitsId) {
        this.companyTraitsIds.remove(companyTraitsId);
    }

    public void setCompanyTraits(Set<String> companyTraitsIds) {
        this.companyTraitsIds = companyTraitsIds;
    }

    @JsonIgnore
    public Set<String> getPersonalTraits() {
        return personalTraitsIds;
    }

    public void addPersonalTraits(String personalTraitsId) {
        this.personalTraitsIds.add(personalTraitsId);
    }

    public void removePersonalTraits(String personalTraitsId) {
        this.personalTraitsIds.remove(personalTraitsId);
    }

    public void setPersonalTraits(Set<String> personalTraitsIds) {
        this.personalTraitsIds = personalTraitsIds;
    }

    @JsonIgnore
    public Set<String> getSocialNetworks() {
        return socialNetworksIds;
    }

    public void addSocialNetworks(String socialNetworksId) {
        this.socialNetworksIds.add(socialNetworksId);
    }

    public void removeSocialNetworks(String socialNetworksId) {
        this.socialNetworksIds.remove(socialNetworksId);
    }

    public void setSocialNetworks(Set<String> socialNetworksIds) {
        this.socialNetworksIds = socialNetworksIds;
    }

    @JsonIgnore
    public Set<String> getJobOffers() {
        return jobOffersIds;
    }

    public void addJobOffer(String jobOfferId) {
        this.jobOffersIds.add(jobOfferId);
    }

    public void removeJobOffer(String jobOfferId) {
        this.jobOffersIds.remove(jobOfferId);
    }

    public void setJobOffers(Set<String> jobOffersIds) {
        this.jobOffersIds = jobOffersIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TalentQueryModel talent = (TalentQueryModel) o;

        return !(talent.id == null || id == null) && Objects.equals(id, talent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TalentQueryModel{" +
            "id=" + id +
            ", email='" + email + "'" +
            ", password='" + password + "'" +
            '}';
    }
}
