package com.easemob.im.server.api.chatgroups.update;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

public class GroupUpdateRequest {

    @JsonProperty("allowinvites")
    private Boolean memberCanInviteOthers;

    @JsonProperty("membersonly")
    private Boolean needApproveToJoin;

    @JsonProperty("maxusers")
    private Integer maxMembers;

    public GroupUpdateRequest() {
        this.memberCanInviteOthers = null;
        this.needApproveToJoin = null;
        this.maxMembers = null;
    }

    @JsonCreator
    public GroupUpdateRequest(@JsonProperty("allowinvites") Boolean memberCanInviteOthers,
                              @JsonProperty("membersonly") Boolean needApproveToJoin,
                              @JsonProperty("maxusers") Integer maxMembers) {
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.maxMembers = maxMembers;
    }

    public GroupUpdateRequest setMemberCanInviteOthers(boolean memberCanInviteOthers) {
        this.memberCanInviteOthers = memberCanInviteOthers;
        return this;
    }

    public GroupUpdateRequest setNeedApproveToJoin(boolean needApproveToJoin) {
        this.needApproveToJoin = needApproveToJoin;
        return this;
    }

    public GroupUpdateRequest setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
        return this;
    }

    public Boolean getMemberCanInviteOthers() {
        return memberCanInviteOthers;
    }

    public Boolean getNeedApproveToJoin() {
        return needApproveToJoin;
    }

    public Integer getMaxMembers() {
        return maxMembers;
    }

}
