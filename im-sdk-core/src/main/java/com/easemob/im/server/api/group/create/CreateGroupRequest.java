package com.easemob.im.server.api.group.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


//groupname	群组名称，此属性为必须的
//desc	群组描述，此属性为必须的
//public	是否是公开群，此属性为必须的
//maxusers	群组成员最大数（包括群主），值为数值类型，默认值200，最大值2000，此属性为可选的
//allowinvites	是否允许群成员邀请别人加入此群。 true：允许群成员邀请人加入此群，false：只有群主或者管理员才可以往群里加人。注：如果是公开群（public为true），则不允许群成员邀请别人加入此群
//members_only	用户申请入群是否需要群主或者群管理员审批，默认是false。注：如果允许了群成员邀请用户进群（allowinvites为true），那么就不需要群主或群管理员审批了
//owner	群组的管理员，此属性为必须的
//members	群组成员，此属性为可选的，但是如果加了此项，数组元素至少一个，不能超过100个（注：群主user1不需要写入到members里面）

public class CreateGroupRequest {

    @JsonProperty("groupname")
    private String groupName = "";

    @JsonProperty("desc")
    private String description = "";

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

    @JsonProperty("membersonly")
    private boolean needApproveToJoin;

    public CreateGroupRequest(boolean isPublic, String owner, List<String> members, int maxMembers, boolean memberCanInviteOthers, boolean needApproveToJoin) {
        this.isPublic = isPublic;
        this.owner = owner;
        this.members = members;
        this.maxMembers = maxMembers;
        this.memberCanInviteOthers = memberCanInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
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

    public boolean isNeedApproveToJoin() {
        return needApproveToJoin;
    }

}
