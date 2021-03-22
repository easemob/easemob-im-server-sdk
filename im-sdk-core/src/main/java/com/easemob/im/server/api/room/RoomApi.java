package com.easemob.im.server.api.room;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.room.admin.demote.DemoteRoomAdmin;
import com.easemob.im.server.api.room.admin.promote.PromoteRoomAdmin;
import com.easemob.im.server.api.room.admin.list.ListRoomAdmins;
import com.easemob.im.server.api.room.create.CreateRoom;
import com.easemob.im.server.api.room.detail.GetRoomDetail;
import com.easemob.im.server.api.room.list.ListRooms;
import com.easemob.im.server.api.room.list.ListRoomsResponse;
import com.easemob.im.server.api.room.member.add.AddRoomMember;
import com.easemob.im.server.api.room.member.remove.RemoveRoomMember;
import com.easemob.im.server.api.room.member.list.ListRoomMembersResponse;
import com.easemob.im.server.api.room.member.list.ListRoomMembers;
import com.easemob.im.server.api.room.superadmin.demote.DemoteRoomSuperAdmin;
import com.easemob.im.server.api.room.superadmin.list.ListRoomSuperAdmins;
import com.easemob.im.server.api.room.superadmin.promote.PromoteRoomSuperAdmin;
import com.easemob.im.server.api.room.update.UpdateRoom;
import com.easemob.im.server.api.room.update.UpdateRoomRequest;
import com.easemob.im.server.model.EMPage;
import com.easemob.im.server.model.EMRoom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/** 聊天室API。
 * 支持聊天室管理：
 * - 创建聊天室
 * - 获取聊天室详情
 * - 修改聊天室
 * - 获取聊天室列表
 * - 获取用户加入的聊天室列表
 * 支持聊天室成员管理：
 * - 获取聊天室成员列表
 * - 添加聊天室成员
 * - 移除聊天室成员
 * 支持聊天室管理员管理：
 * - 获取聊天室管理员
 * - 添加聊天室管理员
 *
 * TODO：支持聊天室超级管理员管理
 *
 * @see com.easemob.im.server.api.block.BlockApi
 */
public class RoomApi {
    private static final List<String> EMPTY_MEMBER_LIST = new ArrayList<>();

    private static final int DEFAULT_MAX_MEMBERS = 200;

    private Context context;

    public RoomApi(Context context) {
        this.context = context;
    }

    /**
     * 创建聊天室。
     *
     * @param name 聊天室名称
     * @param description 聊天室描述
     * @param owner 聊天室主
     * @param members 聊天室初始成员的用户名列表
     * @param maxMembers 聊天室最大成员数
     * @return 聊天室id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%9B%E5%BB%BA%E8%81%8A%E5%A4%A9%E5%AE%A4">创建聊天室</a>
     *
     */
    public Mono<String> createRoom(String name, String description, String owner, List<String> members, int maxMembers) {
        return CreateRoom.createRoom(this.context, name, description, owner, members, maxMembers);
    }

    /**
     * 获取聊天室详情。
     *
     * @param id 聊天室id
     * @return 聊天室详情或错误.
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E8%AF%A6%E6%83%85">获取聊天室详情</a>
     */
    public Mono<EMRoom> getRoom(String id) {
        return GetRoomDetail.byId(this.context, id);
    }


    /**
     * 修改聊天室。
     *
     * 可修改的字段参考 {@link com.easemob.im.server.api.room.update.UpdateRoomRequest UpdateRoomRequest}
     *
     * 比如，要更新聊天室名称，可以这么做:
     * <pre>{@code
     * EMService service;
     * service.updateRoom(roomId, request -> request.withName("some cool name")).block(timeout);
     * }</pre>
     *
     * @param id 聊天室id
     * @param customizer 更新请求定制函数
     * @return 成功或错误
     * @see com.easemob.im.server.api.room.update.UpdateRoomRequest
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E4%BF%AE%E6%94%B9%E8%81%8A%E5%A4%A9%E5%AE%A4%E4%BF%A1%E6%81%AF">修改聊天室</a>
     */
    public Mono<Void> updateRoom(String id, Consumer<UpdateRoomRequest> customizer) {
        return UpdateRoom.byId(this.context, id, customizer);
    }

    /**
     * 获取全部聊天室列表
     *
     * @return 每个聊天室的id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96_app_%E4%B8%AD%E6%89%80%E6%9C%89%E7%9A%84%E8%81%8A%E5%A4%A9%E5%AE%A4">获取聊天室列表</a>
     */
    public Flux<String> listRoomsAll() {
        return ListRooms.all(this.context, 10);
    }

    /**
     * 分页获取聊天室列表
     *
     * @param limit 返回多少个聊天室id
     * @param cursor 开始位置
     * @return 获取聊天室响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96_app_%E4%B8%AD%E6%89%80%E6%9C%89%E7%9A%84%E8%81%8A%E5%A4%A9%E5%AE%A4">获取聊天室列表</a>
     */
    public Mono<EMPage<String>> listRooms(int limit, String cursor) {
        return ListRooms.next(this.context, limit, cursor);
    }

    /**
     * 获取用户加入的聊天室列表。
     *
     * @param username 用户名
     * @return 每个聊天室的id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%8A%A0%E5%85%A5%E7%9A%84%E8%81%8A%E5%A4%A9%E5%AE%A4">获取用户加入的聊天室</a>
     */
    public Flux<String> listRoomsUserJoined(String username) {
        return ListRooms.userJoined(this.context, username);
    }

    /**
     * 获取聊天室全部成员列表。
     *
     * @param roomId 聊天室id
     * @return 每个聊天室成员或者错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">获取聊天室成员</a>
     */
    public Flux<String> listRoomMembersAll(String roomId) {
        return ListRoomMembers.all(this.context, roomId, 10);
    }

    /**
     * 分页获取聊天室成员列表。
     *
     * @param roomId 聊天室id
     * @param limit 返回多少个聊天室成员
     * @param cursor 开始位置
     * @return 获取聊天室成员响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">获取聊天室成员</a>
     */
    public Mono<EMPage<String>> listRoomMembers(String roomId, int limit, String cursor) {
        return ListRoomMembers.next(this.context, roomId, limit, cursor);
    }

    /**
     * 向聊天室添加成员。
     *
     * @param roomId 聊天室id
     * @param username 要添加的用户的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E5%8D%95%E4%B8%AA%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">聊天室添加成员</a>
     */
    public Mono<Void> addRoomMember(String roomId, String username) {
        return AddRoomMember.single(this.context, roomId, username);
    }

    /**
     * 从聊天室移除成员。
     *
     * @param roomId 聊天室id
     * @param username 要移除的成员的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%A0%E9%99%A4%E5%8D%95%E4%B8%AA%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">聊天室移除成员</a>
     */
    public Mono<Void> removeRoomMember(String roomId, String username) {
        return RemoveRoomMember.single(this.context, roomId, username);
    }

    /**
     * 获取聊天室管理员。
     *
     * @param roomId 聊天室id
     * @return 每个管理员的用户名或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98%E5%88%97%E8%A1%A8">获取聊天室管理员</a>
     */
    public Flux<String> listRoomAdminsAll(String roomId) {
        return ListRoomAdmins.all(this.context, roomId);
    }

    /**
     * 升级聊天室成员至管理员。
     *
     * @param roomId 聊天室id
     * @param username 要升级的成员的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98">添加聊天室管理员</a>
     */
    public Mono<Void> promoteRoomAdmin(String roomId, String username) {
        return PromoteRoomAdmin.single(this.context, roomId, username);
    }

    /**
     * 降级聊天室管理员至成员。
     *
     * @param roomId 聊天室id
     * @param username 要降级的管理员的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E7%A7%BB%E9%99%A4%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98">移除聊天室管理员</a>
     */
    public Mono<Void> demoteRoomAdmin(String roomId, String username) {
        return DemoteRoomAdmin.single(this.context, roomId, username);
    }
    /**
     * List Room Super Admins
     *
     * @return A {@code Flux} of super admin's username
     */
    public Flux<String> listRoomSuperAdminsAll(){
        return ListRoomSuperAdmins.all(this.context, 10);
    }

    /**
     * Promote room super admin to member
     *
     * @param username the member's username
     * @return A {code Mono} which completes upon success.
     */
    public Mono<Void> promoteRoomSuperAdmin(String username){
        return PromoteRoomSuperAdmin.single(this.context, username);
    }

    /**
     * Demote room super admin to member
     *
     * @param username the super admin's username
     * @return A {@code Mono} which completes upon success.
     */
    public Mono<Void> demoteRoomSuperAdmin(String username) {
        return DemoteRoomSuperAdmin.singnle(this.context, username);
    }


}
