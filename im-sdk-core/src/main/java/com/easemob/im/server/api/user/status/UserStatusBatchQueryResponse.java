package com.easemob.im.server.api.user.status;

import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMUserStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserStatusBatchQueryResponse {

    @JsonProperty("data")
    private List<Map<String, String>> userStatusList;

    @JsonCreator
    public UserStatusBatchQueryResponse(@JsonProperty("data") List<Map<String, String>> userStatusList) {
        this.userStatusList = userStatusList;
    }

    public List<EMUserStatus> getUsersOnline() {
        if (this.userStatusList == null || this.userStatusList.size() <= 0) {
            throw new EMUnknownException("isUsersOnline api return result is null or empty");
        }
        List<EMUserStatus> emUserStatusList = new ArrayList<>();
        for (Map<String, String> map : this.userStatusList) {
            map.forEach((username, status) -> {
                Boolean isOnline = "online".equals(status);
                EMUserStatus emUserStatus = new EMUserStatus(username, isOnline);
                emUserStatusList.add(emUserStatus);
            });
        }
        return emUserStatusList;
    }
}
