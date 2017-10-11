package io.bandit.limbo.limbo.modules.shared.model;

import java.util.List;

public class Paginated<T> {

    private final int pageSize;
    private final List<T> content;
    private final int totalPages;
    private final int totalElements;
    private final int pageNumber;

    public Paginated(final List<T> content,
                     final int totalPages,
                     final long totalElements,
                     final int number,
                     final int size) {

        this.content = content;
        this.totalPages = (totalElements == 0) ? 0 : totalPages;;
        this.totalElements = (int) totalElements;
        this.pageNumber = number;
        this.pageSize = size;
    }

    public int getPageSize() {
        return (totalElements > pageSize) ? pageSize : totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPreviousPageNumber() {
        return (pageNumber > 2) ? pageNumber -1 : pageNumber;
    }

    public int getNextPageNumber() {
        return ((pageNumber+1) < totalPages) ? pageNumber +1 : totalPages;
    }
}
