package com.easemob.im.server.api.user.status;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class UserStatusBatchQueryResponse {

    @JsonProperty("data")
    private List<Map<String, String>> userStatusList;

    @JsonCreator
    public UserStatusBatchQueryResponse(@JsonProperty("data") List<Map<String, String>> userStatusList) {
        this.userStatusList = userStatusList;
    }

    public List<Map<String, String>> getUserStatusList() {
        return this.userStatusList;
    }
}
