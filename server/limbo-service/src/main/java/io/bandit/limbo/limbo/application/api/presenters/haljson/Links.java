package io.bandit.limbo.limbo.application.api.presenters.haljson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.bandit.limbo.limbo.modules.shared.model.Paginated;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Links implements Serializable{

    @JsonProperty(value = "self", required = true)
    private Link self;

    @JsonProperty(value = "first", required = true)
    private Link first;

    @JsonProperty(value = "prev", required = true)
    private Link prev;

    @JsonProperty(value = "next", required = true)
    private Link next;

    @JsonProperty(value = "last", required = true)
    private Link last;

    public Links(final String url, final String queryParams, final Paginated page) {

        Integer currentPage = 1;
        Integer previous = 1;
        Integer next = 1;
        Integer last = 1;

        if (page.getTotalPages() > 0) {
            currentPage = page.getPageNumber() + 1;
            previous = (currentPage == 1) ? 1 : (currentPage - 1);
            next = (currentPage == page.getTotalPages()) ? currentPage : currentPage + 1;
            last = page.getTotalPages();
        }

        this.self = new Link(createPaginationUrl(url, queryParams, currentPage));
        this.first = new Link(createPaginationUrl(url, queryParams, 1));
        this.prev = new Link(createPaginationUrl(url, queryParams, previous));
        this.next = new Link(createPaginationUrl(url, queryParams, next));
        this.last = new Link(createPaginationUrl(url, queryParams, last));
    }

    public static String normalizedUrl(final String url) {
        try {
            final URIBuilder uriBuilder = new URIBuilder(url);

            return uriBuilder.toString()
                    .replace(uriBuilder.getHost(), "")
                    .replace(uriBuilder.getScheme() + "://", "")
                    .replace(":" + uriBuilder.getPort(), "")
                    ;
        } catch (Throwable ignored) {
            return url;
        }
    }


    public static String createPaginationUrl(final String url, final String urlQueryParams, final Integer value) {

        try {
            String uri = url.contains("://") ? url : "http://example.com/"+url;
            if (null != urlQueryParams && urlQueryParams.length()>0) {
                uri = uri+"?"+urlQueryParams;
            }

            final Boolean[] hasPageNumber = {false};
            final URIBuilder uriBuilder = new URIBuilder(uri);
            final List<NameValuePair> queryParams = uriBuilder.getQueryParams();
            final List<NameValuePair> newQueryParams = new ArrayList<>();

            queryParams.forEach(nameValuePair -> {
                if (nameValuePair.getName().equals("page[number]")) {
                    hasPageNumber[0] = true;
                    newQueryParams.add(new BasicNameValuePair("page[number]", value.toString()));
                } else {
                    newQueryParams.add(nameValuePair);
                }
            });

            if (!hasPageNumber[0]) {
                newQueryParams.add(new BasicNameValuePair("page[number]", value.toString()));
            }

            uriBuilder.setParameters(newQueryParams);

            return uriBuilder.toString()
                    .replace(uriBuilder.getHost(), "")
                    .replace(uriBuilder.getScheme()+"://", "")
                    .replace(":"+uriBuilder.getPort(), "")
                    ;

        } catch (Throwable ignored) {
            ignored.printStackTrace();
        }

        return null;
    }

    public Link getSelf() {
        return self;
    }

    public Link getFirst() {
        return first;
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
