package com.easemob.im.server.api.group;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupMemberResource {

    @JsonProperty("member")
    private String memberUsername;

    @JsonProperty("owner")
    private String ownerUsername;

    @JsonCreator
    public GroupMemberResource(@JsonProperty("member") String memberUsername,
                               @JsonProperty("owner") String ownerUsername) {
        this.memberUsername = memberUsername;
        this.ownerUsername = ownerUsername;
    }

    public String getUsername() {
        return this.memberUsername != null ? this.memberUsername : this.ownerUsername;
    }

}
