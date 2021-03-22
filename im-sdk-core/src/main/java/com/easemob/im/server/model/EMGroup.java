package com.easemob.im.server.model;

public class EMGroup extends EMEntity {

    private final String name;

    private final String description;

    private final boolean isPublic;

    private final boolean needApproveToJoin;

    private final boolean canMemberInviteOthers;

    private final String owner;

    private final int maxMembers;

    public EMGroup(String groupId, String name, String description, boolean isPublic, boolean needApproveToJoin, boolean canMemberInviteOthers,
                   String owner, int maxMembers) {
        super(EntityType.GROUP);
        super.id(groupId);
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.needApproveToJoin = needApproveToJoin;
        this.canMemberInviteOthers = canMemberInviteOthers;
        this.owner = owner;
        this.maxMembers = maxMembers;
    }

    public String getGroupId() {
        return super.id();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
}
