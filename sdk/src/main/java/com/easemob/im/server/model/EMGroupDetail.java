package com.easemob.im.server.model;

import com.easemob.im.server.api.chatgroups.detail.GroupDetailResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EMGroupDetail {
    private final String groupId;

    private final boolean isPublic;

    private final boolean needApproveToJoin;

    private final boolean memberCanInviteOthers;

    private final int maxMembers;

    private final List<EMGroupMember> members;

    public EMGroupDetail(String groupId, boolean isPublic, boolean needApproveToJoin, boolean memberCanInviteOthers, int maxMembers, List<EMGroupMember> members) {
        this.groupId = groupId;
        this.isPublic = isPublic;
        this.needApproveToJoin = needApproveToJoin;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.maxMembers = maxMembers;
        this.members = members;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public boolean getIsPublic() {
        return this.isPublic;
    }

    public boolean getNeedApproveToJoin() {
        return this.needApproveToJoin;
    }

    public boolean getMemberCanInviteOthers() {
        return this.memberCanInviteOthers;
    }

    public int getMaxMembers() {
        return this.maxMembers;
    }

    public List<EMGroupMember> getMembers() {
        return this.members;
    }

}
