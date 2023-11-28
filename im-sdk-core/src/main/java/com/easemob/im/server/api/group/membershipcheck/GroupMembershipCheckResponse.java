package com.easemob.im.server.api.group.membershipcheck;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupMembershipCheckResponse {

    @JsonProperty("data")
    private Boolean data;

    @JsonCreator
    public GroupMembershipCheckResponse(@JsonProperty("data") Boolean data) {
        this.data = data;
    }

    public Boolean getData() {
        return data;
    }
}
