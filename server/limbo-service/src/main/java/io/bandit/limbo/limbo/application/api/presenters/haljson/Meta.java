package io.bandit.limbo.limbo.application.api.presenters.haljson;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Meta implements Serializable{

    @JsonProperty(value = "current_page", required = true)
    private Integer current;

    @JsonProperty(value = "page_size", required = true)
    private Integer size;

    @JsonProperty(value = "total_pages", required = true)
    private Integer total;

    @JsonProperty(value = "total_elements", required = true)
    private Integer totalElements;

    public Meta(long totalElements, Integer total, Integer current, Integer size) {
        this.totalElements = Integer.valueOf(""+totalElements);
        this.total = total;
        this.current = current + 1;
        this.size = size;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public Integer getCurrent() {
        return current;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getTotal() {
        return total;
    }
}
