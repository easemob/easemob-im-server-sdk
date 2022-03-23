package com.easemob.im.server.api.group.settings;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateGroupRequest {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("allowinvites")
    private Boolean canMemberInviteOthers;

    @JsonProperty("membersonly")
    private Boolean needApproveToJoin;

    @JsonProperty("maxusers")
    private Integer maxMembers;

    @JsonProperty("custom")
    private String custom;

    public UpdateGroupRequest() {
        this.name = null;
        this.description = null;
        this.canMemberInviteOthers = null;
        this.needApproveToJoin = null;
        this.maxMembers = null;
        this.custom = null;
    }

    @JsonCreator
    public UpdateGroupRequest(@JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("allowinvites") Boolean canMemberInviteOthers,
            @JsonProperty("membersonly") Boolean needApproveToJoin,
            @JsonProperty("maxusers") Integer maxMembers) {
        this.name = name;
        this.description = description;
        this.canMemberInviteOthers = canMemberInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.maxMembers = maxMembers;
    }

    @JsonCreator
    public UpdateGroupRequest(@JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("allowinvites") Boolean canMemberInviteOthers,
            @JsonProperty("membersonly") Boolean needApproveToJoin,
            @JsonProperty("maxusers") Integer maxMembers,
            @JsonProperty("custom") String custom) {
        this.name = name;
        this.description = description;
        this.canMemberInviteOthers = canMemberInviteOthers;
        this.needApproveToJoin = needApproveToJoin;
        this.maxMembers = maxMembers;
        this.custom = custom;
    }

    public String getName() {
        return name;
    }

    public UpdateGroupRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public UpdateGroupRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getCanMemberInviteOthers() {
        return canMemberInviteOthers;
    }

    public UpdateGroupRequest setCanMemberInviteOthers(Boolean canMemberInviteOthers) {
        this.canMemberInviteOthers = canMemberInviteOthers;
        return this;
    }

    public Boolean getNeedApproveToJoin() {
        return needApproveToJoin;
    }

    public UpdateGroupRequest setNeedApproveToJoin(Boolean needApproveToJoin) {
        this.needApproveToJoin = needApproveToJoin;
        return this;
    }

    public Integer getMaxMembers() {
        return maxMembers;
    }

    public UpdateGroupRequest setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
        return this;
    }

    public String getCustom() {
        return custom;
    }

    public UpdateGroupRequest setCustom(String custom) {
        this.custom = custom;
        return this;
    }
}
