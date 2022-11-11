package com.easemob.im.server.model;

import java.util.Map;

public class EMMetadataBatch {
    private Map<String, Map<String, String>> data;

    public EMMetadataBatch(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    @Override public String toString() {
        return "EMMetadataBatch{" +
                "data=" + data +
                '}';
    }
}
