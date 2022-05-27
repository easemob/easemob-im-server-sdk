package com.easemob.im.server.api.group.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateGroupRequest {

    @JsonProperty("groupname")
    private String groupName;

    @JsonProperty("desc")
    private String description;

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
            boolean needApproveToJoin, String custom, boolean needVerify) {
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
            boolean needVerify) {
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

}
