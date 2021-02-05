package com.easemob.im.server.api.chatgroups;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.chatgroups.announcement.GroupAnnouncement;
import com.easemob.im.server.api.chatgroups.create.GroupCreate;
import com.easemob.im.server.api.chatgroups.create.GroupCreateRequest;
import com.easemob.im.server.api.chatgroups.create.GroupCreateResponse;
import com.easemob.im.server.api.chatgroups.delete.GroupDelete;
import com.easemob.im.server.api.chatgroups.detail.GroupDetails;
import com.easemob.im.server.api.chatgroups.list.GroupList;
import com.easemob.im.server.api.chatgroups.list.GroupListResponse;
import com.easemob.im.server.api.chatgroups.update.GroupUpdate;
import com.easemob.im.server.api.chatgroups.update.GroupUpdateRequest;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMGroupDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

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
     * @return A {@code Mono} which emit {@code EMGroup} if successful.
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
     * Delete this group.
     *
     * @return A {@code Mono} complete on success.
     */
    public Mono<Void> deleteGroup(String groupId) {
        return GroupDelete.execute(this.context, groupId);
    }

    /**
     * List all groups.
     *
     * To control the page by yourself, you can use the listGroups api instead.
     *
     * @param pageSize the page size, 20 is a good start point.
     *                 A higher value gives better I/O efficiency, while a smaller value give lower latency.
     * @return A {@code Flux} which emits {@code EMGroup} on success.
     */
    public Flux<EMGroup> listAllGroups(int pageSize) {
        return GroupList.all(this.context, pageSize);
    }

    /**
     * List groups in one page.
     * For the first page, pass {@code null} in cursor. To get the next page, you need to pass the cursor returned from previous response.
     * <pre>{@code
     *  EMService service;
     *  GroupListResponse response = service.listGroups(10, null).block();
     *  List<EMGroup> groups = response.getEMGroups();
     *  // ... do something to the groups ...
     *  String cursor = response.getCursor();
     *  while (cursor != null) {
     *      response = service.listGroups(10, cursor);
     *      // ... do something to the groups ...
     *      cursor = response.getCursor();
     *  }
     * }</pre>
     *
     * @param pageSize the page size, 20 is a good start point.
     *                 A higher value gives better I/O efficiency, while a smaller value give lower latency.
     * @param cursor where to continue, returned in previous {@code GroupListResponse}
     * @return A {@code Mono} emits {@code GroupListResponse} on success.
     */
    public Mono<GroupListResponse> listGroups(int pageSize, String cursor) {
        return GroupList.next(this.context, pageSize, cursor);
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
     * @param customizer update request customizer
     * @return A {@code Mono} complete if successful.
     */
    public Mono<Void> updateGroupSettings(String groupId, Function<GroupUpdateRequest, GroupUpdateRequest> customizer) {
        return new GroupUpdate(this.context, groupId, customizer.apply(new GroupUpdateRequest()))
            .execute();
    }

    /**
     * Get the group announcement.
     *
     * @return A {@code Mono} emits the announcement on success.
     */
    public Mono<String> getGroupAnnouncement(String groupId) {
        return GroupAnnouncement.get(this.context, groupId);
    }

    /**
     * Update the group announcement.
     * @param announcement the announcement
     * @return A {@code Mono} which complete on success.
     */
    public Mono<Void> updateGroupAnnouncement(String groupId, String announcement) {
        return GroupAnnouncement.update(this.context, groupId, announcement);
    }
}
