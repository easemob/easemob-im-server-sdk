package com.easemob.im.server.model;

import java.util.Map;

public class EMMetadata {
    private Map<String, String> data;

    public EMMetadata(Map<String, String> data) {
        this.data = data;
    }

    public Map<String, String> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "EMMetadata{" +
                "data=" + data +
                '}';
    }
}
