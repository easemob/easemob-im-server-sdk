package com.easemob.im.server.api.group.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateGroupRequest {

    @JsonProperty("groupid")
    private String groupId;

    @JsonProperty("groupname")
    private String groupName;

    @JsonProperty("desc")
    private String description;

    @JsonProperty("scale")
    private String scale;

    @JsonProperty("public")
    private boolean isPublic;

    @JsonProperty("owner")
    private String owner;

    @JsonProperty("members")
    private List<String> members;

    @JsonProperty("maxusers")
    private int maxMembers;

    @JsonProperty("allowinvites")
    private boolean memberCanInviteOthers;

    @JsonProperty("invite_need_confirm")
    private boolean needInviteConfirm;

    @JsonProperty("members_only")
    private boolean needApproveToJoin;

    @JsonProperty("custom")
    private String custom;

    @JsonProperty("need_verify")
    private boolean needVerify;

    {
        this.needVerify = true;
    }

    @JsonProperty("avatar")
    private String avatar;

    @SuppressWarnings("java:S107")
    public CreateGroupRequest(String groupName, String description, boolean isPublic, String owner,
            List<String> members, int maxMembers, boolean memberCanInviteOthers,
            boolean needApproveToJoin) {
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
    }

    @SuppressWarnings("java:S107")
    public CreateGroupRequest(String groupName, String description, boolean isPublic, String owner,
            List<String> members, int maxMembers, boolean memberCanInviteOthers,
            boolean needApproveToJoin, String custom) {
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
    }

    public CreateGroupRequest(String groupName, String description, boolean isPublic, String owner,
            List<String> members, int maxMembers, boolean memberCanInviteOthers,
            boolean needApproveToJoin, String custom, boolean needVerify, String avatar) {
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
        this.needVerify = needVerify;
        this.avatar = avatar;
    }

    public CreateGroupRequest(String groupName, String description, boolean isPublic, String scale, String owner,
            List<String> members, int maxMembers, boolean memberCanInviteOthers,
            boolean needApproveToJoin, String custom, boolean needVerify, String avatar) {
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.scale = scale;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
        this.needVerify = needVerify;
        this.avatar = avatar;
    }

    public CreateGroupRequest(String groupId, String groupName, String description,
            boolean isPublic, String owner,
            List<String> members, int maxMembers, boolean memberCanInviteOthers,
            boolean needApproveToJoin, String custom, boolean needVerify, String avatar) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
        this.needVerify = needVerify;
        this.avatar = avatar;
    }

    public CreateGroupRequest(String groupId, String groupName, String description,
            boolean isPublic, String scale, String owner, List<String> members, int maxMembers,
            boolean memberCanInviteOthers, boolean needApproveToJoin, String custom,
            boolean needVerify, String avatar) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.scale = scale;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
        this.needVerify = needVerify;
        this.avatar = avatar;
    }

    @SuppressWarnings("java:S107")
    public CreateGroupRequest(String groupName, String description, boolean isPublic, String owner,
            List<String> members, int maxMembers, boolean memberCanInviteOthers,
            boolean needInviteConfirm, boolean needApproveToJoin, String custom) {
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needInviteConfirm = needInviteConfirm;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
    }

    @SuppressWarnings("java:S107")
    public CreateGroupRequest(String groupName, String description, boolean isPublic, String owner,
            List<String> members, int maxMembers, boolean memberCanInviteOthers,
            boolean needInviteConfirm, boolean needApproveToJoin, String custom,
            boolean needVerify, String avatar) {
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needInviteConfirm = needInviteConfirm;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
        this.needVerify = needVerify;
        this.avatar = avatar;
    }

    @SuppressWarnings("java:S107")
    public CreateGroupRequest(String groupName, String description, boolean isPublic, String scale,
            String owner, List<String> members, int maxMembers, boolean memberCanInviteOthers,
            boolean needInviteConfirm, boolean needApproveToJoin, String custom,
            boolean needVerify, String avatar) {
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.scale = scale;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needInviteConfirm = needInviteConfirm;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
        this.needVerify = needVerify;
        this.avatar = avatar;
    }

    @SuppressWarnings("java:S107")
    public CreateGroupRequest(String groupId, String groupName, String description, boolean isPublic, String owner,
            List<String> members, int maxMembers, boolean memberCanInviteOthers,
            boolean needInviteConfirm, boolean needApproveToJoin, String custom,
            boolean needVerify, String avatar) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needInviteConfirm = needInviteConfirm;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
        this.needVerify = needVerify;
        this.avatar = avatar;
    }

    public CreateGroupRequest(String groupId, String groupName, String description,
            boolean isPublic, String scale, String owner, List<String> members, int maxMembers,
            boolean memberCanInviteOthers, boolean needInviteConfirm, boolean needApproveToJoin,
            String custom, boolean needVerify, String avatar) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.isPublic = isPublic;
        this.scale = scale;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needInviteConfirm = needInviteConfirm;
        this.needApproveToJoin = needApproveToJoin;
        this.custom = custom;
        this.needVerify = needVerify;
        this.avatar = avatar;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public String getOwner() {
        return owner;
    }

    public List<String> getMembers() {
        return members;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public boolean isMemberCanInviteOthers() {
        return memberCanInviteOthers;
    }

    public boolean isNeedInviteConfirm() {
        return needInviteConfirm;
    }

    public boolean isNeedApproveToJoin() {
        return needApproveToJoin;
    }

    public boolean isNeedVerify(){
        return needVerify;
    }

    public String getCustom() {
        return custom;
    }

    public String getAvatar() {
        return avatar;
    }
}
