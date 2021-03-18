package com.easemob.im.server.api.group.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupUpdateRequest {

    @JsonProperty("allowinvites")
    private Boolean canMemberInviteOthers;

    @JsonProperty("membersonly")
    private Boolean needApproveToJoin;

    @JsonProperty("maxusers")
    private Integer maxMembers;

    public GroupUpdateRequest() {
        this.canMemberInviteOthers = null;
        this.needApproveToJoin = null;
        this.maxMembers = null;
    }

    @JsonCreator
    public GroupUpdateRequest(@JsonProperty("allowinvites") Boolean canMemberInviteOthers,
                              @JsonProperty("membersonly") Boolean needApproveToJoin,
                              @JsonProperty("maxusers") Integer maxMembers) {
        this.canMemberInviteOthers = canMemberInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.maxMembers = maxMembers;
    }

    public GroupUpdateRequest setCanMemberInviteOthers(boolean canMemberInviteOthers) {
        this.canMemberInviteOthers = canMemberInviteOthers;
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

    public Boolean getCanMemberInviteOthers() {
        return canMemberInviteOthers;
    }

    public Boolean getNeedApproveToJoin() {
        return needApproveToJoin;
    }

    public Integer getMaxMembers() {
        return maxMembers;
    }

}
