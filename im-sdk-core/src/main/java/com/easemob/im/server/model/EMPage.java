package com.easemob.im.server.model;

import java.util.List;

public class EMPage<T> {

    private List<T> values;

    private String cursor;

    public EMPage(List<T> values, String cursor) {
        this.values = values;
        this.cursor = cursor;
    }

    public List<T> getValues() {
        return this.values;
    }

    public String getCursor() {
        return this.cursor;
    }

}
