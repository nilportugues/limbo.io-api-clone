package io.bandit.limbo.limbo.modules.main.socialnetworks.infrastructure.elasticsearch;
import com.fasterxml.jackson.annotation.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SocialNetworks Read Model.
 */
@Document(indexName = "v1_socialnetworks", type = "socialnetworks")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SocialNetworksQueryModel implements Serializable {



    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "url")
    private String url;

    @JsonProperty(value = "talent")
    private String talentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }
    @JsonIgnore
    public String getTalent() {
        return talentId;
    }

    public void setTalent(String talentId) {
        this.talentId = talentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SocialNetworksQueryModel socialNetworks = (SocialNetworksQueryModel) o;

        return !(socialNetworks.id == null || id == null) && Objects.equals(id, socialNetworks.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SocialNetworksQueryModel{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", url='" + url + "'" +
            '}';
    }
}
