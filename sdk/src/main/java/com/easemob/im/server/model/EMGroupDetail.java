package com.easemob.im.server.model;

import com.easemob.im.server.api.chatgroups.detail.GroupDetailResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class EMGroupDetail {
    private final boolean isPublic;

    private final boolean needApproveToJoin;

    private final boolean memberCanInviteOthers;

    private final int maxMembers;

    private final List<EMGroupMember> members;

    public EMGroupDetail(boolean isPublic, boolean needApproveToJoin, boolean memberCanInviteOthers, int maxMembers, List<EMGroupMember> members) {
        this.isPublic = isPublic;
        this.needApproveToJoin = needApproveToJoin;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.maxMembers = maxMembers;
        this.members = members;
    }

}
