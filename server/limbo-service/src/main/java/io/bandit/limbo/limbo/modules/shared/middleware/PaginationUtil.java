package io.bandit.limbo.limbo.modules.shared.middleware;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URISyntaxException;

/**
 * Utility class for handling pagination.
 *
 * Pagination follows <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 */
public final class PaginationUtil {

    private PaginationUtil(){
    }

    public static HttpHeaders generatePaginationHttpHeaders(Integer next, Integer prev, Integer last, Integer first, Integer total)
    throws URISyntaxException {
        return generatePaginationHttpHeaders(
            String.valueOf(next),
            String.valueOf(prev),
            String.valueOf(last),
            String.valueOf(first),
            total
        );
    }

    public static HttpHeaders generatePaginationHttpHeaders(String next, String prev, String last, String first, Integer total)
    throws URISyntaxException {

        final HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", "" + total);

        String link = "";

        if (next.length()>0) {
            link = "<" + next + ">; rel=\"next\",";
        }

        if (prev.length()>0) {
            link += "<" + prev + ">; rel=\"prev\",";
        }

        if (last.length()>0) {
            link += "<" + last + ">; rel=\"last\",";
        }

        if (first.length()>0) {
            link += "<" + first + ">; rel=\"first\"";
        }

        headers.add(HttpHeaders.LINK, link);

        return headers;
    }

    private static String generateUri(String baseUrl, int page, int size) throws URISyntaxException {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }
}
