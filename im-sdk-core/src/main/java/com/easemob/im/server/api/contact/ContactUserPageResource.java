package com.easemob.im.server.api.contact;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactUserPageResource {

    @JsonProperty("username")
    private String username;

    @JsonProperty("remark")
    private String remark;

    @JsonCreator
    public ContactUserPageResource(
            @JsonProperty("username") String username,
            @JsonProperty("remark") String remark) {
        this.username = username;
        this.remark = remark;
    }

    public String getUsername() {
        return username;
    }

    public String getRemark() {
        return remark;
    }

    @Override public String toString() {
        return "UserResource{" +
                "username='" + username + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
