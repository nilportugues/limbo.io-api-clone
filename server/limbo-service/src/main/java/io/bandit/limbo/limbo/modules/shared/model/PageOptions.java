package io.bandit.limbo.limbo.modules.shared.model;

public class PageOptions {
    private Integer number;
    private Integer size;
    private String limit;
    private String cursor;
    private String offset;

    public PageOptions() {

    }

    public static PageOptions forNumericPage(Integer pageNumber, Integer pageSize) {
        final PageOptions pageOptions = new PageOptions();
        pageOptions.setNumber(pageNumber);
        pageOptions.setSize(pageSize);

        return pageOptions;
    }

    public static PageOptions forCursorPage(String limit, String cursor) {
        final PageOptions pageOptions = new PageOptions();
        pageOptions.setLimit(limit);
        pageOptions.setCursor(cursor);

        return pageOptions;
    }

    public int getNumber() {
        return number;
    }

    public PageOptions setNumber(Integer number) {
        this.number = number;

        if (this.number<1) {
            this.number = 1;
        }

        return this;
    }

    public PageOptions setNumber(String number) {

        this.number = Integer.valueOf(number);

        if (this.number<1) {
            this.number = 1;
        }

        return this;
    }

    public int getSize() {
        return size;
    }

    public PageOptions setSize(String size) {
        this.size = Integer.valueOf(size);

        if (this.size <1) {
            this.size = 1;
        }

        return this;
    }

    public PageOptions setSize(Integer size) {
        this.size = size;

        if (this.size <1) {
            this.size = 1;
        }

        return this;
    }

    public String getLimit() {
        return limit;
    }

    public PageOptions setLimit(String limit) {
        this.limit = limit;

        return this;
    }

    public String getCursor() {
        return cursor;
    }

    public PageOptions setCursor(String cursor) {
        this.cursor = cursor;

        return this;
    }

    public String getOffset() {
        return offset;
    }

    public PageOptions setOffset(String offset) {
        this.offset = offset;

        return this;
    }
}
