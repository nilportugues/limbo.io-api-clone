package io.bandit.limbo.limbo.application.api.presenters.haljson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Link implements Serializable {
    @JsonProperty(value = "href", required = true)
    private String href;

    public Link(final String href) {
        this.href = href;
    }

    public String getHref() {
        return href;
    }
}
