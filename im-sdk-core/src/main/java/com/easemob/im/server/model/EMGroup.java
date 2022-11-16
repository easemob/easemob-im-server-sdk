package com.easemob.im.server.model;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import io.netty.util.internal.StringUtil;

import java.util.regex.Pattern;

public class EMGroup extends EMEntity {

    private static final Pattern GROUP_ID_PATTERN = Pattern.compile("^[1-9][0-9]{1,17}$");

    private final String name;

    private final String description;

    private final boolean isPublic;

    private final boolean needApproveToJoin;

    private final boolean canMemberInviteOthers;

    private final String owner;

    private final int maxMembers;

    private final int affiliationsCount;

    private final Affiliation affiliations;

    private final boolean isMute;

    private final String custom;

    public EMGroup(String groupId, String name, String description, boolean isPublic,
            boolean needApproveToJoin, boolean canMemberInviteOthers,
            String owner, int maxMembers, int affiliationsCount, String[] groupMembers,
            boolean isMute, String custom) {
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
        this.isMute = isMute;
        this.custom = custom;
    }

    public static void validateGroupId(String groupId) {
        if (StringUtil.isNullOrEmpty(groupId)) {
            throw new EMInvalidArgumentException("groupId must not be null or blank");
        }
        if (!GROUP_ID_PATTERN.matcher(groupId).matches()) {
            throw new EMInvalidArgumentException(
                    String.format("groupId '%s' should match regex %s", groupId,
                            GROUP_ID_PATTERN));
        }
    }

    public String getGroupId() {
        return super.id();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
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

    public boolean getIsMute() {
        return this.isMute;
    }

    public String getCustom() {
        return this.custom;
    }
}
