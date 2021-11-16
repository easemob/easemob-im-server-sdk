package com.easemob.im.server.model;

public class EMGroup extends EMEntity {

    private final String name;

    private final String description;

    private final boolean isPublic;

    private final boolean needApproveToJoin;

    private final boolean canMemberInviteOthers;

    private final String owner;

    private final int maxMembers;

    private final int affiliationsCount;

    private final Affiliation affiliations;

    public EMGroup(String groupId, String name, String description, boolean isPublic,
                   boolean needApproveToJoin, boolean canMemberInviteOthers,
                   String owner, int maxMembers, int affiliationsCount, String[] groupMembers) {
        super(EntityType.GROUP);
        super.id(groupId);
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.needApproveToJoin = needApproveToJoin;
        this.canMemberInviteOthers = canMemberInviteOthers;
        this.owner = owner;
        this.maxMembers = maxMembers;
        this.affiliationsCount = affiliationsCount;
        this.affiliations = new Affiliation(owner, groupMembers);
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

    public int getAffiliationsCount() {
        return this.affiliationsCount;
    }

    public Affiliation getAffiliations() {
        return this.affiliations;
    }

    public class Affiliation {
        private final String owner;

        private final String[] members;

        public Affiliation(String owner, String[] members) {
            this.owner = owner;
            this.members = members;
        }

        public String getOwner() {
            return this.owner;
        }

        public String[] getMembers() {
            return this.members;
        }
    }
}
