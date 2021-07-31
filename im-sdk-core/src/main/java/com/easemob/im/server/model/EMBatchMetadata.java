package com.easemob.im.server.model;

import java.util.Map;

public class EMBatchMetadata {
    private Map<String, Map<String, String>> data;

    public EMBatchMetadata(Map<String, Map<String, String>> data) {
        this.data = data;
    }

    public Map<String, Map<String, String>> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "EMMetadata{" +
                "data=" + data +
                '}';
    }
}
