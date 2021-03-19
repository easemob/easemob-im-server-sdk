package com.easemob.im.server.model;

import java.util.List;

public class EMGroup extends EMEntity {

    private final boolean isPublic;

    private final boolean needApproveToJoin;

    private final boolean canMemberInviteOthers;

    private final String owner;

    private final int maxMembers;

    private final List<String> members;

    public EMGroup(String groupId, boolean isPublic, boolean needApproveToJoin, boolean canMemberInviteOthers,
                   String owner, int maxMembers, List<String> members) {
        super(EntityType.GROUP);
        super.id(groupId);
        this.isPublic = isPublic;
        this.needApproveToJoin = needApproveToJoin;
        this.canMemberInviteOthers = canMemberInviteOthers;
        this.owner = owner;
        this.maxMembers = maxMembers;
        this.members = members;
    }

    public String getGroupId() {
        return super.id();
    }

    public boolean getIsPublic() {
        return this.isPublic;
    }

    public boolean getNeedApproveToJoin() {
        return this.needApproveToJoin;
    }

    public boolean getCanMemberInviteOthers() {
        return this.canMemberInviteOthers;
    }

    public String getOwner() {
        return this.owner;
    }

    public int getMaxMembers() {
        return this.maxMembers;
    }

    public List<String> getMembers() {
        return this.members;
    }

}
