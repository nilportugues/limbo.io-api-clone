package io.bandit.limbo.limbo.modules.shared.model;

import java.util.ArrayList;
import java.util.List;

public class SortOptions {

    private List<String> ascending = new ArrayList<String>();
    private List<String> descending = new ArrayList<String>();

    public void addAscending(String field)
    {
        ascending.add(field);
    }

    public void addDescending(String field)
    {
        descending.add(field);
    }

    public List<String> getAscending() {
        return ascending;
    }

    public List<String> getDescending() {
        return descending;
    }

    public boolean isEmpty() {
        return 0 == descending.size() && 0 == ascending.size();
    }
}
