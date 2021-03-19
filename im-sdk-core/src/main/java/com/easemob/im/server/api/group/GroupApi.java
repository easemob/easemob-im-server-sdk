package com.easemob.im.server.api.group;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.group.admin.GroupAdminAdd;
import com.easemob.im.server.api.group.admin.GroupAdminList;
import com.easemob.im.server.api.group.admin.GroupAdminRemove;
import com.easemob.im.server.api.group.announcement.GroupAnnouncement;
import com.easemob.im.server.api.group.create.CreateGroup;
import com.easemob.im.server.api.group.delete.DeleteGroup;
import com.easemob.im.server.api.group.get.GetGroup;
import com.easemob.im.server.api.group.list.GroupList;
import com.easemob.im.server.api.group.list.GroupListResponse;
import com.easemob.im.server.api.group.member.add.GroupMemberAdd;
import com.easemob.im.server.api.group.member.list.GroupMemberList;
import com.easemob.im.server.api.group.member.list.GroupMemberListResponse;
import com.easemob.im.server.api.group.member.remove.GroupMemberRemove;
import com.easemob.im.server.api.group.settings.UpdateGroup;
import com.easemob.im.server.api.group.settings.UpdateGroupRequest;
import com.easemob.im.server.model.EMGroupAdmin;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMGroupMember;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

/**
 * 群API。
 * 支持群管理：
 * - 创建群
 * - 删除群
 * - 获取群列表
 * - 获取群详情
 * - 获取用户加入的群
 * - 修改群详情
 * - 修改群主
 * 支持群成员管理：
 * - 获取群成员列表
 * - 添加群成员
 * - 删除群成员
 * 支持群管理员管理：
 * - 获取群管理员列表
 * - 添加群管理员
 * - 删除群管理员
 *
 * 群与聊天室都是多人聊天，与聊天室主要差别在于群支持离线消息，即群成员上线时可以收到离线时错过的消息。
 * 如果配置了推送，则离线消息也会产生推送。
 * 群分为公开群和私有群，区别在于：在设备SDK中（指iOS、Android、Web、小程序等），私有群不会出现在群列表API的返回结果。
 *
 * @see com.easemob.im.server.api.block.BlockApi
 */
public class GroupApi {

    private Context context;

    private GroupList groupList;
    private CreateGroup createGroup;
    private DeleteGroup deleteGroup;

    private GetGroup getGroup;
    private UpdateGroup updateGroup;

    private GroupAnnouncement groupAnnouncement;
    private GroupMemberList groupMemberList;
    private GroupMemberAdd groupMemberAdd;
    private GroupMemberRemove groupMemberRemove;

    private GroupAdminList groupAdminList;
    private GroupAdminAdd groupAdminAdd;
    private GroupAdminRemove groupAdminRemove;

    public GroupApi(Context context) {
        this.context = context;
        this.groupList = new GroupList(context);
        this.createGroup = new CreateGroup(context);
        this.deleteGroup = new DeleteGroup(context);

        this.getGroup = new GetGroup(context);
        this.updateGroup = new UpdateGroup(context);

        this.groupAnnouncement = new GroupAnnouncement(context);

        this.groupMemberList = new GroupMemberList(context);
        this.groupMemberAdd = new GroupMemberAdd(context);
        this.groupMemberRemove = new GroupMemberRemove(context);

        this.groupAdminList = new GroupAdminList(context);
        this.groupAdminAdd = new GroupAdminAdd(context);
        this.groupAdminRemove = new GroupAdminRemove(context);

    }

    /**
     * 创建公开群。
     * 需要注意的是，目前公开群不允许成员邀请其他用户加入。如果要允许，可以用修改群API设置:
     * <pre>{@code
     *      EMService service;
     *      service.group().updateSetting("group-id", settings -> settings.memberCanInvite(true)).block();
     * }</pre>
     *
     * @param owner 群主的用户名
     * @param members 初始群成员的用户名列表
     * @param maxMembers 群最大成员数
     * @param needApproveToJoin 新成员加入需要管理员审批
     * @return 群id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E7%BE%A4%E7%BB%84">创建群</a>
     */
    public Mono<String> createPublicGroup(String owner, List<String> members, int maxMembers, boolean needApproveToJoin) {
        return this.createGroup.publicGroup(owner, members, maxMembers, needApproveToJoin);
    }

    /**
     * 创建私有群。
     *
     * @param owner 群主的用户名
     * @param members 初始群成员的用户名列表
     * @param maxMembers 群最大成员数
     * @param canMemberInvite 新成员加入需要管理员审批
     * @return 群id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E5%88%9B%E5%BB%BA%E4%B8%80%E4%B8%AA%E7%BE%A4%E7%BB%84">创建群</a>
     */
    public Mono<String> createPrivateGroup(String owner, List<String> members, int maxMembers, boolean canMemberInvite) {
        return this.createGroup.privateGroup(owner, members, maxMembers, canMemberInvite);
    }

    /**
     * 注销群。
     *
     * @param groupId 群id
     * @return 成功或错误
     */
    public Mono<Void> destroyGroup(String groupId) {
        return this.deleteGroup.execute(groupId);
    }

    /**
     * 获取全部群列表。
     *
     * @return 每个群id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96app%E4%B8%AD%E6%89%80%E6%9C%89%E7%9A%84%E7%BE%A4%E7%BB%84_%E5%8F%AF%E5%88%86%E9%A1%B5">获取群列表</a>
     */
    public Flux<String> listAllGroups() {
        return this.groupList.all(20);
    }

    /**
     * 分页获取群列表。
     *
     * 初次调用时，{@code cursor} 传 {@code null}。之后的调用，{@code cursor} 传上次返回的值。
     *
     * 可以这样遍历群列表：
     * <pre>{@code
     *  EMService service;
     *  GroupListResponse response = service.listGroups(20, null).block();
     *  List<String> groupIds = response.getGroupIds();
     *  // ... do something with the groupIds ...
     *  String cursor = response.getCursor();
     *  // cursor == null indicates the end of the list
     *  while (cursor != null) {
     *      response = service.listGroups(20, cursor);
     *      // ... do something to the groupIds ...
     *      cursor = response.getCursor();
     *  }
     * }</pre>
     *
     * @param limit 每次取回多少个群id
     * @param cursor 上次返回的{@code cursor}
     * @return 群列表响应或错误
     * @see GroupListResponse
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96app%E4%B8%AD%E6%89%80%E6%9C%89%E7%9A%84%E7%BE%A4%E7%BB%84_%E5%8F%AF%E5%88%86%E9%A1%B5">获取群列表</a>
     */
    public Mono<GroupListResponse> listGroups(int limit, String cursor) {
        return this.groupList.next(limit, cursor);
    }

    /**
     * List groups user joined.
     *
     * @param username the username
     * @return A {@code Flux} which emits {@code EMGroup} on successful.
     */
    public Flux<String> listGroupsUserJoined(String username) {
        return this.groupList.userJoined(username);
    }

    /**
     * 获取群详情。
     *
     * @param groupId 群id
     * @return 群详情或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%BB%84%E8%AF%A6%E6%83%85">获取群详情</a>
     */
    public Mono<EMGroup> getGroup(String groupId) {
        return this.getGroup.execute(groupId);
    }

    /**
     * 修改群详情。
     * 支持修改的参数见{@code GroupSettingsUpdateRequest}
     * 比如，更新群最大成员数：
     * <pre>{@code
     * EMService service;
     * service.group().updateSettings("1", settings -> settings.maxMembers(100)).block();
     * }</pre>
     *
     * @param groupId 群id
     * @param customizer 请求定制器
     * @return 成功或错误
     * @see UpdateGroupRequest
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E4%BF%AE%E6%94%B9%E7%BE%A4%E7%BB%84%E4%BF%A1%E6%81%AF">修改群详情</a>
     */
    public Mono<Void> updateGroup(String groupId, Consumer<UpdateGroupRequest> customizer) {
        return this.updateGroup.update(groupId, customizer);
    }

    /**
     * 修改群主。新群主需要已经是群成员，否则会报错{@code EMForbiddenException}。
     *
     * @param groupId 群id
     * @param username 新群主的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E8%BD%AC%E8%AE%A9%E7%BE%A4%E7%BB%84">修改群主</a>
     */
    public Mono<Void> updateGroupOwner(String groupId, String username) {
        return this.updateGroup.updateOwner(groupId, username);
    }

    /**
     * 获取群公告。
     *
     * @param groupId 群id
     * @return 群公告或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%BB%84%E5%85%AC%E5%91%8A">获取群公告</a>
     */
    public Mono<String> getGroupAnnouncement(String groupId) {
        return this.groupAnnouncement.get(groupId);
    }

    /**
     * 更新群公告。
     *
     * @param groupId 群id
     * @param announcement 群公告
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E4%BF%AE%E6%94%B9%E7%BE%A4%E7%BB%84%E5%85%AC%E5%91%8A">更新群公告</a>
     */
    public Mono<Void> updateGroupAnnouncement(String groupId, String announcement) {
        return this.groupAnnouncement.set(groupId, announcement);
    }

    /**
     * 获取群全部成员。
     *
     * @param groupId 群id
     * @return 每个群成员或错误
     * @see com.easemob.im.server.model.EMGroupMember
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98">获取群成员</a>
     */
    public Flux<EMGroupMember> listAllGroupMembers(String groupId) {
        return this.groupMemberList.all(groupId, 20);
    }

    /**
     * 分页获取群成员。
     *
     * 首次调用时，{@code cursor} 传 {@code null}。之后每次调用，{@code cursor} 传上次返回的值。
     *
     * 比如：
     *
     * <pre>{@code
     * EMService service;
     * GroupListResponse response = service.listGroupMemberss("group-id", 10, null).block();
     * List<EMGroupMembers> groups = response.getEMGroups();
     * // ... do something to the members ...
     * String cursor = response.getCursor();
     * while (cursor != null) {
     *     response = service.listGroupMembers("group-id", 10, cursor);
     *     // ... do something to the members ...
     *     cursor = response.getCursor();
     * }
     * }</pre>

     * @param groupId 群id
     * @param limit 返回多少群id
     * @param cursor 开始位置
     * @return 获取群成员响应或错误
     * @see com.easemob.im.server.api.group.member.list.GroupMemberListResponse
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98">获取群成员</a>
     */
    public Mono<GroupMemberListResponse> listGroupMembers(String groupId, int limit, String cursor) {
        return this.groupMemberList.next(groupId, limit, cursor);
    }

    /**
     * 添加群成员。
     *
     * @param groupId 群id
     * @param username 要添加的用户的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E5%8D%95%E4%B8%AA%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98">添加群成员</a>
     */
    public Mono<Void> addGroupMember(String groupId, String username) {
        return this.groupMemberAdd.single(groupId, username);
    }

    /**
     * 移除群成员。
     *
     * @param groupId 群id
     * @param username 要移除的用户的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E7%A7%BB%E9%99%A4%E5%8D%95%E4%B8%AA%E7%BE%A4%E7%BB%84%E6%88%90%E5%91%98">移除群成员</a>
     */
    public Mono<Void> removeGroupMember(String groupId, String username) {
        return this.groupMemberRemove.single(groupId, username);
    }

    /**
     * 获取群全部管理员。
     *
     * @param groupId 群id
     * @return 每个管理员或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E8%8E%B7%E5%8F%96%E7%BE%A4%E7%AE%A1%E7%90%86%E5%91%98%E5%88%97%E8%A1%A8">获取群管理员</a>
     */
    public Flux<EMGroupAdmin> listGroupAdmins(String groupId) {
        return this.groupAdminList.all(groupId);
    }

    /**
     * 升级群成员为群管理员。
     *
     * @param groupId 群id
     * @param username 被升级的群成员的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E6%B7%BB%E5%8A%A0%E7%BE%A4%E7%AE%A1%E7%90%86%E5%91%98">升级群成员</a>
     */
    public Mono<Void> addGroupAdmin(String groupId, String username) {
        return this.groupAdminAdd.single(groupId, username);
    }

    /**
     * 降级群管理员为群成员。
     *
     * @param groupId 群id
     * @param username 被降级的群管理员的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/group#%E7%A7%BB%E9%99%A4%E7%BE%A4%E7%AE%A1%E7%90%86%E5%91%98">降级群管理员</a>
     */
    public Mono<Void> removeGroupAdmin(String groupId, String username) {
        return this.groupAdminRemove.single(groupId, username);
    }



}
