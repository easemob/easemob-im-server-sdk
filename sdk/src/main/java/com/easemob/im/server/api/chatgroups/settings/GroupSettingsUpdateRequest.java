package com.easemob.im.server.api.chatgroups.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupSettingsUpdateRequest {

    @JsonProperty("allowinvites")
    private Boolean memberCanInviteOthers;

    @JsonProperty("membersonly")
    private Boolean needApproveToJoin;

    @JsonProperty("maxusers")
    private Integer maxMembers;

    public GroupSettingsUpdateRequest() {
        this.memberCanInviteOthers = null;
        this.needApproveToJoin = null;
        this.maxMembers = null;
    }

    @JsonCreator
    public GroupSettingsUpdateRequest(@JsonProperty("allowinvites") Boolean memberCanInviteOthers,
                                      @JsonProperty("membersonly") Boolean needApproveToJoin,
                                      @JsonProperty("maxusers") Integer maxMembers) {
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.maxMembers = maxMembers;
    }

    public GroupSettingsUpdateRequest setMemberCanInviteOthers(boolean memberCanInviteOthers) {
        this.memberCanInviteOthers = memberCanInviteOthers;
        return this;
    }

    public GroupSettingsUpdateRequest setNeedApproveToJoin(boolean needApproveToJoin) {
        this.needApproveToJoin = needApproveToJoin;
        return this;
    }

    public GroupSettingsUpdateRequest setMaxMembers(int maxMembers) {
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
