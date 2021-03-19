package com.easemob.im.server.api.group;

import com.easemob.im.server.model.EMGroupMember;
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

    public EMGroupMember toGroupMember() {
        return this.memberUsername != null ? EMGroupMember.asMember(this.memberUsername) : EMGroupMember.asOwner(this.ownerUsername);
    }

    public String getUsername() {
        return this.memberUsername != null ? this.memberUsername : this.ownerUsername;
    }

}
