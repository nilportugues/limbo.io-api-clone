package io.bandit.limbo.limbo.application.api.resources.personaltraits.marshallers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.main.personaltraits.model.PersonalTraits;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Link;
import io.bandit.limbo.limbo.application.api.presenters.haljson.Links;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.stream.Collectors;

@ApiModel
public class PersonalTraitsPaginatedResponse implements PersonalTraitsJsonResponse {

    @ApiModelProperty(name = "page", required = true, position = 1)
    @JsonProperty(value = "page", required = true)
    protected Integer page;

    @ApiModelProperty(name = "pages", required = true, position = 2)
    @JsonProperty(value = "pages", required = true)
    protected Integer pages;

    @ApiModelProperty(name = "count", required = true, position = 3)
    @JsonProperty(value = "count", required = true)
    protected Integer count;

    @ApiModelProperty(name = "total", required = true, position = 4)
    @JsonProperty(value = "total", required = true)
    protected Integer total;

    @ApiModelProperty(name = "_embedded", required = true, position = 5)
    @JsonProperty(value = "_embedded", required = true)
    protected List<PersonalTraitsResponse> embedded;

    @ApiModelProperty(name = "_links", required = true, position = 6)
    @JsonProperty(value = "_links", required = true)
    protected PaginationLinks links;

    public PersonalTraitsPaginatedResponse(
            final String pageUrl,
            final String queryParams,
            final Paginated<PersonalTraits> page) {

        setData(page, pageUrl);
        setLinks(pageUrl, queryParams, page);

        this.page = page.getPageNumber();
        this.pages = page.getTotalPages();
        this.count = page.getPageSize();
        this.total = page.getTotalElements();
    }


    private void setData(final Paginated<PersonalTraits> page, final String pageUrl) {

        embedded = page.getContent()
            .stream()
            .map(v -> PersonalTraitsResponse.fromPersonalTraits(v, pageUrl, pageUrl+"/"+v.getId()))
            .collect(Collectors.toList());
    }

    private void setLinks(final String pageUrl, final String queryParams, final Paginated<PersonalTraits> page) {

        links = new PaginationLinks();

        links.setFirst(new Link(Links.createPaginationUrl(pageUrl, queryParams, 1)));
        links.setLast(new Link(Links.createPaginationUrl(pageUrl, queryParams, page.getTotalPages())));
        links.setPrev(new Link(Links.createPaginationUrl(pageUrl, queryParams, page.getPreviousPageNumber())));
        links.setNext(new Link(Links.createPaginationUrl(pageUrl, queryParams, page.getNextPageNumber())));
        links.setSelf(new Link(Links.createPaginationUrl(pageUrl, queryParams, page.getPageNumber())));
    }


    public static class PaginationLinks {

        private Link self;
        private Link first;
        private Link prev;
        private Link next;
        private Link last;

        public PaginationLinks() {
        }

        public void setFirst(Link first) {
            this.first = first;
        }

        public void setSelf(final Link self) {
            this.self = self;
        }

        public void setPrev(final Link prev) {
            this.prev = prev;
        }

        public void setNext(final Link next) {
            this.next = next;
        }

        public void setLast(final Link last) {
            this.last = last;
        }

        public Link getFirst() {
            return first;
        }

        public Link getSelf() {
            return self;
        }

        public Link getPrev() {
            return prev;
        }

        public Link getNext() {
            return next;
        }

        public Link getLast() {
            return last;
        }
    }
}


