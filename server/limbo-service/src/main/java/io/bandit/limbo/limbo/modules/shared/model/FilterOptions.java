package io.bandit.limbo.limbo.modules.shared.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilterOptions<T> {

    private static final String FILTER_EQUALS = "equals";
    private static final String FILTER_NOT = "not";
    private static final String FILTER_RANGES = "ranges";
    private static final String FILTER_NOT_RANGES = "!ranges";
    private static final String FILTER_IN = "in";
    private static final String FILTER_NOT_IN = "!in";
    private static final String FILTER_GTE = "gte";
    private static final String FILTER_GT = "gt";
    private static final String FILTER_LTE = "lte";
    private static final String FILTER_LT = "lt";
    private static final String FILTER_HAS = "has";
    private static final String FILTER_NOT_HAS = "!has";
    private static final String FILTER_STARTS = "starts";
    private static final String FILTER_ENDS = "ends";
    private static final String FILTER_NOT_STARTS = "!starts";
    private static final String FILTER_NOT_ENDS = "!ends";

    final private Map<String, Map<String, ArrayList<T>>> filters = new HashMap<>();

    public FilterOptions() {
        filters.put(FILTER_NOT, new HashMap<>());
        filters.put(FILTER_RANGES, new HashMap<>());
        filters.put(FILTER_NOT_RANGES, new HashMap<>());
        filters.put(FILTER_IN, new HashMap<>());
        filters.put(FILTER_NOT_IN, new HashMap<>());
        filters.put(FILTER_GTE, new HashMap<>());
        filters.put(FILTER_GT, new HashMap<>());
        filters.put(FILTER_LTE, new HashMap<>());
        filters.put(FILTER_LT, new HashMap<>());
        filters.put(FILTER_HAS, new HashMap<>());
        filters.put(FILTER_NOT_HAS, new HashMap<>());
        filters.put(FILTER_STARTS, new HashMap<>());
        filters.put(FILTER_ENDS, new HashMap<>());
        filters.put(FILTER_NOT_STARTS, new HashMap<>());
        filters.put(FILTER_NOT_ENDS, new HashMap<>());
        filters.put(FILTER_EQUALS, new HashMap<>());
    }

    public void setEquals(String key, ArrayList<T> value) {
        filters.get(FILTER_EQUALS).put(key, value);
    }

    public void setEquals(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_EQUALS).put(key, list);
    }

    public Map<String, ArrayList<T>> getEquals() {
        return filters.get(FILTER_EQUALS);
    }

    public void setNot(String key, ArrayList<T> value) {

        filters.get(FILTER_NOT).put(key, value);
    }

    public void setNot(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_NOT).put(key, list);
    }

    public Map<String, ArrayList<T>> getNot() {
        return filters.get(FILTER_NOT);
    }

    public void setRanges(String key, ArrayList<T> value) {
        filters.get(FILTER_RANGES).put(key, value);
    }

    public void setRanges(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_RANGES).put(key, list);
    }

    public Map<String, ArrayList<T>> getRanges() {
        return filters.get(FILTER_RANGES);
    }

    public void setNotRanges(String key, ArrayList<T> value) {
        filters.get(FILTER_NOT_RANGES).put(key, value);
    }

    public void setNotRanges(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_NOT_RANGES).put(key, list);
    }

    public Map<String, ArrayList<T>> getNotRanges() {
        return filters.get(FILTER_NOT_RANGES);
    }

    public void setIn(String key, ArrayList<T> value) {
        filters.get(FILTER_IN).put(key, value);
    }

    public void setIn(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_IN).put(key, list);
    }

    public Map<String, ArrayList<T>> getIn() {
        return filters.get(FILTER_IN);
    }

    public void setNotIn(String key, ArrayList<T> value) {
        filters.get(FILTER_NOT_IN).put(key, value);
    }

    public void setNotIn(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_NOT_IN).put(key, list);
    }

    public Map<String, ArrayList<T>> getNotIn() {
        return filters.get(FILTER_NOT_IN);
    }


    public void setGreaterThanOrEqual(String key, ArrayList<T> value) {
        filters.get(FILTER_GTE).put(key, value);
    }

    public void setGreaterThanOrEqual(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_GTE).put(key, list);
    }

    public Map<String, ArrayList<T>> getGreaterThanOrEqual() {
        return filters.get(FILTER_GTE);
    }


    public void setGreaterThan(String key, ArrayList<T> value) {
        filters.get(FILTER_GT).put(key, value);
    }

    public void setGreaterThan(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_GT).put(key, list);
    }

    public Map<String, ArrayList<T>> getGreaterThan() {
        return filters.get(FILTER_GT);
    }


    public void setLessThan(String key, ArrayList<T> value) {
        filters.get(FILTER_LT).put(key, value);
    }

    public void setLessThan(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_LT).put(key, list);
    }

    public Map<String, ArrayList<T>> getLessThan() {
        return filters.get(FILTER_LT);
    }

    public void setLessThanOrEqual(String key, ArrayList<T> value) {
        filters.get(FILTER_LTE).put(key, value);
    }

    public void setLessThanOrEqual(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_LTE).put(key, list);
    }

    public Map<String, ArrayList<T>> getLessThanOrEqual() {
        return filters.get(FILTER_LTE);
    }


    public void setHas(String key, ArrayList<T> value) {
        filters.get(FILTER_HAS).put(key, value);
    }

    public void setHas(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_HAS).put(key, list);
    }

    public Map<String, ArrayList<T>> getHas() {
        return filters.get(FILTER_HAS);
    }


    public void setNotHas(String key, ArrayList<T> value) {
        filters.get(FILTER_NOT_HAS).put(key, value);
    }

    public void setNotHas(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_NOT_HAS).put(key, list);
    }

    public Map<String, ArrayList<T>> getNotHas() {
        return filters.get(FILTER_NOT_HAS);
    }


    public void setNotStarts(String key, ArrayList<T> value) {
        filters.get(FILTER_NOT_STARTS).put(key, value);
    }

    public void setNotStarts(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_NOT_STARTS).put(key, list);
    }

    public Map<String, ArrayList<T>> getNotStarts() {
        return filters.get(FILTER_NOT_STARTS);
    }


    public void setNotEnds(String key, ArrayList<T> value) {
        filters.get(FILTER_NOT_ENDS).put(key, value);
    }

    public void setNotEnds(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_NOT_ENDS).put(key, list);
    }

    public Map<String, ArrayList<T>> getNotEnds() {
        return filters.get(FILTER_NOT_ENDS);
    }


    public void setStarts(String key, ArrayList<T> value) {
        filters.get(FILTER_STARTS).put(key, value);
    }

    public void setStarts(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_STARTS).put(key, list);
    }

    public Map<String, ArrayList<T>> getStarts() {
        return filters.get(FILTER_STARTS);
    }


    public void setEnds(String key, ArrayList<T> value) {
        filters.get(FILTER_ENDS).put(key, value);
    }

    public void setEnds(String key, T value) {
        final ArrayList<T> list = new ArrayList<>();
        list.add(value);

        filters.get(FILTER_ENDS).put(key, list);
    }

    public Map<String, ArrayList<T>> getEnds() {
        return filters.get(FILTER_ENDS);
    }
}

