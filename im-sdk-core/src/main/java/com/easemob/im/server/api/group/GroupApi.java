package com.easemob.im.server.api.group;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.group.admin.GroupAdminAdd;
import com.easemob.im.server.api.group.admin.GroupAdminList;
import com.easemob.im.server.api.group.admin.GroupAdminRemove;
import com.easemob.im.server.api.group.announcement.GroupAnnouncement;
import com.easemob.im.server.api.group.crud.*;
import com.easemob.im.server.api.group.detail.GroupDetails;
import com.easemob.im.server.api.group.member.add.GroupMemberAdd;
import com.easemob.im.server.api.group.member.list.GroupMemberList;
import com.easemob.im.server.api.group.member.list.GroupMemberListResponse;
import com.easemob.im.server.api.group.member.remove.GroupMemberRemove;
import com.easemob.im.server.api.group.settings.GroupSettings;
import com.easemob.im.server.api.group.settings.GroupSettingsUpdateRequest;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMGroupAdmin;
import com.easemob.im.server.model.EMGroupDetails;
import com.easemob.im.server.model.EMGroupMember;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

public class GroupApi {
    private static final int PUBLIC_GROUP_MAX_MEMBERS_DEFAULT = 200;
    private static final boolean PUBLIC_GROUP_NEED_APPROVE_TO_JOIN_DEFAULT = false;
    private static final int PRIVATE_GROUP_MAX_MEMBERS_DEFAULT = 200;
    private static final boolean PRIVATE_GROUP_MEMBER_CAN_INVITE_OTHERS_DEFAULT = false;

    private Context context;

    public GroupApi(Context context) {
        this.context = context;
    }

    /**
     * Create a public group.
     * By default, member of public group could not invite others to join.
     * To allow member invite others, you can update group settings like this:
     * <pre>{@code
     *      EMService service;
     *      service.group().updateSetting("group-id", settings -> settings.memberCanInvite(true)).block();
     * }</pre>
     *
     * @param owner the owner's username
     * @param members the initial members, could be null or empty to create an empty group
     * @return A {@code Mono} which emits {@code EMGroup} on success.
     */
    public Mono<EMGroup> createPublicGroup(String owner, List<String> members) {
        return GroupCreate.publicGroup(this.context, owner, members, PUBLIC_GROUP_MAX_MEMBERS_DEFAULT, PUBLIC_GROUP_NEED_APPROVE_TO_JOIN_DEFAULT);
    }

    /**
     * Create a public group.
     * By default, member of public group could not invite others to join.
     * To allow member invite others, you can update group settings like this:
     * <pre>{@code
     *      EMService service;
     *      service.group().updateSetting("group-id", settings -> settings.memberCanInvite(true)).block();
     * }</pre>
     *
     * @param owner the owner's username
     * @param members the initial members
     * @param maxMembers how many members could join this group
     * @param needApproveToJoin whether user joining this group have to wait, until owner/admin approve it
     * @return A {@code Mono} which emits {@code EMGroup} on success.
     */
    public Mono<EMGroup> createPublicGroup(String owner, List<String> members, int maxMembers, boolean needApproveToJoin) {
        return GroupCreate.publicGroup(this.context, owner, members, maxMembers, needApproveToJoin);
    }

    /**
     * Create a private group.
     *
     * @param owner the owner's username
     * @param members the initial members
     * @return A {@code Mono} which emits {@code EMGroup} if successful.
     */
    public Mono<EMGroup> createPrivateGroup(String owner, List<String> members) {
        return GroupCreate.privateGroup(this.context, owner, members, PRIVATE_GROUP_MAX_MEMBERS_DEFAULT, PRIVATE_GROUP_MEMBER_CAN_INVITE_OTHERS_DEFAULT);
    }

    /**
     * Create a private group.
     *
     * @param owner the owner's username
     * @param members the initial members
     * @param maxMembers how many members could join this group
     * @param canMemberInvite can member invite others
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
     */
    public Mono<EMGroup> createPrivateGroup(String owner, List<String> members, int maxMembers, boolean canMemberInvite) {
        return GroupCreate.privateGroup(this.context, owner, members, maxMembers, canMemberInvite);
    }

    /**
     * Destroy this group.
     * @param groupId the group id
     * @return A {@code Mono} completes on success.
     */
    public Mono<Void> destroyGroup(String groupId) {
        return GroupDestroy.execute(this.context, groupId);
    }

    /**
     * List all groups.
     *
     * Note that listAllGroups will send requests recursively until the end.
     * You can use the listGroups api to control when to send next request.
     *
     * @return A {@code Flux} which emits {@code EMGroup} on success.
     */
    public Flux<EMGroup> listAllGroups() {
        return GroupList.all(this.context, 20);
    }

    /**
     * List groups in one page.
     *
     * At the first call, pass {@code null} in cursor.
     * Then you need to pass the cursor returned from previous response.
     *
     * <pre>{@code
     *  EMService service;
     *  GroupListResponse response = service.listGroups(20, null).block();
     *  List<EMGroup> groups = response.getEMGroups();
     *  // ... do something to the groups ...
     *  String cursor = response.getCursor();
     *  while (cursor != null) {
     *      response = service.listGroups(20, cursor);
     *      // ... do something to the groups ...
     *      cursor = response.getCursor();
     *  }
     * }</pre>
     *
     * @param limit the limit, controls max members returns each time
     * @param cursor the cursor received in the previous response
     * @return A {@code Mono} emits {@code GroupListResponse} on success.
     */
    public Mono<GroupListResponse> listGroups(int limit, String cursor) {
        return GroupList.next(this.context, limit, cursor);
    }

    /**
     * List groups user joined.
     *
     * @param username the username
     * @return A {@code Flux} which emits {@code EMGroup} on successful.
     */
    public Flux<EMGroup> listGroupsUserJoined(String username) {
        return GroupList.userJoined(this.context, username);
    }

    /**
     * Get this group detail.
     *
     * To get group details,
     * <pre>{@code
     *      EMService service;
     *      EMGroupDetails details = service.group().detail("1").block();
     * }</pre>
     *
     * @param groupId the group id
     * @return A {@code Mono} emits {@code EMGroupDetail} on success.
     */
    public Mono<EMGroupDetails> getGroupDetails(String groupId) {
        return GroupDetails.execute(this.context, groupId);
    }

    /**
     * Update this group's settings.
     *
     * To update max members of a group:
     * <pre>{@code
     *     EMService service;
     *     service.group().updateSettings("1", settings -> settings.maxMembers(100)).block();
     * }</pre>
     *
     * @param groupId the group id
     * @param customizer update request customizer
     * @return A {@code Mono} completes on successful.
     */
    public Mono<Void> updateSettings(String groupId, Consumer<GroupSettingsUpdateRequest> customizer) {
        return GroupSettings.update(this.context, groupId, customizer);
    }

    /**
     * Get the group announcement.
     *
     * @param groupId the group id
     * @return A {@code Mono} emits the announcement on success.
     */
    public Mono<String> getGroupAnnouncement(String groupId) {
        return GroupAnnouncement.get(this.context, groupId);
    }

    /**
     * Update the group announcement.
     * @param groupId the group id
     * @param announcement the announcement
     * @return A {@code Mono} which completes on success.
     */
    public Mono<Void> updateGroupAnnouncement(String groupId, String announcement) {
        return GroupAnnouncement.update(this.context, groupId, announcement);
    }

    /**
     * List all members of a group. This method is recommended over {@code getGroupDetails}, since
     * Note that listAllGroupMembers send requests recursively until the end.
     * You call use listGroupMembers to control when to send next request.
     *
     * @param groupId the group id
     * @param limit the limit groups requested each time, 20 is a good start point.
     *              Tune it higher to get better I/O efficiency, smaller to get lower latency.
     * @return A {@code Flux} emits {@code EMGroupMember}.
     */
    public Flux<EMGroupMember> listAllGroupMembers(String groupId, int limit) {
        return GroupMemberList.all(this.context, groupId, limit);
    }

    /**
     * List members of a group.
     *
     * At the first call, pass {@code null} in cursor.
     * Then you need to pass the cursor returned from previous response.
     *
     * <pre>{@code
     *  EMService service;
     *  GroupListResponse response = service.listGroupMemberss("group-id", 10, null).block();
     *  List<EMGroupMembers> groups = response.getEMGroups();
     *  // ... do something to the members ...
     *  String cursor = response.getCursor();
     *  while (cursor != null) {
     *      response = service.listGroupMembers("group-id", 10, cursor);
     *      // ... do something to the members ...
     *      cursor = response.getCursor();
     *  }
     * }</pre>

     * @param groupId the group id
     * @param limit the limit, controls max members returns each time
     * @param cursor the cursor received in the previous response
     * @return A {@code Mono} emits {@code GroupMemberListResponse}.
     */
    public Mono<GroupMemberListResponse> listGroupMembers(String groupId, int limit, String cursor) {
        return GroupMemberList.next(this.context, groupId, limit, cursor);
    }

    /**
     * Add a user to the group.
     *
     * @param groupId the group id
     * @param username the username
     * @return A {@code Mono} which completes on success.
     */
    public Mono<Void> addGroupMember(String groupId, String username) {
        return GroupMemberAdd.single(this.context, groupId, username);
    }

    /**
     * Remove a member from the group.
     *
     * @param groupId the group id
     * @param username the username
     * @return A {@code Mono} which completes on success.
     */
    public Mono<Void> removeGroupMember(String groupId, String username) {
        return GroupMemberRemove.single(this.context, groupId, username);
    }

    /**
     * List all admins of the group.
     *
     * @param groupId the group id
     * @return A {@code Flux} emits {@code EMGroupAdmin}.
     */
    public Flux<EMGroupAdmin> listGroupAdmins(String groupId) {
        return GroupAdminList.all(this.context, groupId);
    }

    /**
     * Promote a member of the group to be admin.
     *
     * @param groupId the group id
     * @param username the username
     * @return A {@code Mono} which completes on success.
     */
    public Mono<Void> addGroupAdmin(String groupId, String username) {
        return GroupAdminAdd.single(this.context, groupId, username);
    }

    /**
     * Demote an admin of the group to be member.
     *
     * @param groupId the group id
     * @param username the username
     * @return A {@code Mono} which completes on success.
     */
    public Mono<Void> removeGroupAdmin(String groupId, String username) {
        return GroupAdminRemove.single(this.context, groupId, username);
    }

    /**
     * Update owner of the group.
     *
     * @param groupId the group id
     * @param username the username of new owner
     * @return A {@code Mono} which completes upon success.
     */
    public Mono<Void> updateGroupOwner(String groupId, String username) {
        return GroupUpdate.owner(this.context, groupId, username);
    }

}
