package io.bandit.limbo.limbo.modules.main.socialnetworks.model;

import io.bandit.limbo.limbo.modules.shared.model.Aggregate;
import io.bandit.limbo.limbo.modules.main.talent.model.Talent;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.*;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksNameChanged;
import io.bandit.limbo.limbo.modules.main.socialnetworks.event.SocialNetworksUrlChanged;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

/**
 * A SocialNetworks Domain Entity.
 */
public class SocialNetworks extends Aggregate {

    private String id;

    private String name;
    private String url;
    private Talent talent;

    public static SocialNetworks create(String id, String name, String url) {
        final SocialNetworks self = new SocialNetworks();
        self.id = id;
        self.name = name;
        self.url = url;

        return self;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Throwable {

        if (!Objects.equals(id, this.id) && this.id != null) {
            throw new SocialNetworksImmutableFieldException("id");
        }

        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {

        if (null != name && !name.equals(this.name)) {
            this.name = name;
            apply(new SocialNetworksNameChanged(this));
        }
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

        if (null != url && !url.equals(this.url)) {
            this.url = url;
            apply(new SocialNetworksUrlChanged(this));
        }
    }
    public Talent getTalent() {
        return talent;
    }

    public void setTalent(Talent talent) {
        if (!Objects.equals(this.talent, talent)) {
            removeTalent();
            this.talent = talent;
            apply(new SocialNetworksTalentChanged(this));
        }
    }

    public void removeTalent() {
        if (null != this.talent) {
            apply(new SocialNetworksTalentChanged(this));
            this.talent = null;
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
        final SocialNetworks socialNetworks = (SocialNetworks) o;

        return !(socialNetworks.id == null || id == null) && Objects.equals(id, socialNetworks.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SocialNetworks{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", url='" + url + "'" +
            '}';
    }
}
