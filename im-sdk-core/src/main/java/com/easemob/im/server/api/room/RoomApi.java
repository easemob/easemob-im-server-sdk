package com.easemob.im.server.api.room;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.room.admin.demote.DemoteRoomAdmin;
import com.easemob.im.server.api.room.admin.list.ListRoomAdmins;
import com.easemob.im.server.api.room.admin.promote.PromoteRoomAdmin;
import com.easemob.im.server.api.room.announcement.RoomAnnouncement;
import com.easemob.im.server.api.room.assign.AssignRoom;
import com.easemob.im.server.api.room.create.CreateRoom;
import com.easemob.im.server.api.room.delete.DeleteRoom;
import com.easemob.im.server.api.room.detail.GetRoomDetail;
import com.easemob.im.server.api.room.list.ListRooms;
import com.easemob.im.server.api.room.member.add.AddRoomMember;
import com.easemob.im.server.api.room.member.list.ListRoomMembers;
import com.easemob.im.server.api.room.member.list.ListRoomMembersResponseV1;
import com.easemob.im.server.api.room.member.remove.RemoveRoomMember;
import com.easemob.im.server.api.room.superadmin.demote.DemoteRoomSuperAdmin;
import com.easemob.im.server.api.room.superadmin.list.ListRoomSuperAdmins;
import com.easemob.im.server.api.room.superadmin.promote.PromoteRoomSuperAdmin;
import com.easemob.im.server.api.room.update.UpdateRoom;
import com.easemob.im.server.api.room.update.UpdateRoomRequest;
import com.easemob.im.server.model.EMGroup;
import com.easemob.im.server.model.EMPage;
import com.easemob.im.server.model.EMRoom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 聊天室API。
 */
public class RoomApi {

    private CreateRoom createRoom;

    private GetRoomDetail getRoomDetail;

    private UpdateRoom updateRoom;

    private ListRooms listRooms;

    private ListRoomMembers listRoomMembers;

    private AddRoomMember addRoomMember;

    private RemoveRoomMember removeRoomMember;

    private ListRoomAdmins listRoomAdmins;

    private PromoteRoomAdmin promoteRoomAdmin;

    private DemoteRoomAdmin demoteRoomAdmin;

    private ListRoomSuperAdmins listRoomSuperAdmins;

    private PromoteRoomSuperAdmin promoteRoomSuperAdmin;

    private DemoteRoomSuperAdmin demoteRoomSuperAdmin;

    private DeleteRoom deleteRoom;

    private AssignRoom assignRoom;

    private RoomAnnouncement roomAnnouncement;

    public RoomApi(Context context) {
        this.createRoom = new CreateRoom(context);
        this.getRoomDetail = new GetRoomDetail(context);
        this.updateRoom = new UpdateRoom(context);
        this.listRooms = new ListRooms(context);
        this.listRoomMembers = new ListRoomMembers(context);
        this.addRoomMember = new AddRoomMember(context);
        this.removeRoomMember = new RemoveRoomMember(context);
        this.listRoomAdmins = new ListRoomAdmins(context);
        this.promoteRoomAdmin = new PromoteRoomAdmin(context);
        this.demoteRoomAdmin = new DemoteRoomAdmin(context);
        this.listRoomSuperAdmins = new ListRoomSuperAdmins(context);
        this.promoteRoomSuperAdmin = new PromoteRoomSuperAdmin(context);
        this.demoteRoomSuperAdmin = new DemoteRoomSuperAdmin(context);
        this.deleteRoom = new DeleteRoom(context);
        this.assignRoom = new AssignRoom(context);
        this.roomAnnouncement=new RoomAnnouncement(context);
    }

    /**
     * 创建聊天室。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * List<String> members = new ArrayList<>();
     * members.add("userA");
     *
     * try {
     *     String roomId = service.room().createRoom("name", "description", "owner", members, 200).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param name        聊天室名称
     * @param description 聊天室描述
     * @param owner       聊天室主
     * @param members     聊天室初始成员的用户名列表
     * @param maxMembers  聊天室最大成员数
     * @return 聊天室id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%9B%E5%BB%BA%E8%81%8A%E5%A4%A9%E5%AE%A4">创建聊天室</a>
     */
    public Mono<String> createRoom(String name, String description, String owner,
            List<String> members, int maxMembers) {
        return this.createRoom.createRoom(name, description, owner, members, maxMembers);
    }

    /**
     * 创建聊天室。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * List<String> members = new ArrayList<>();
     * members.add("userA");
     *
     * try {
     *     String roomId = service.room().createRoom("name", "description", "owner", members, 200).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param name        聊天室名称
     * @param description 聊天室描述
     * @param owner       聊天室主
     * @param members     聊天室初始成员的用户名列表
     * @param maxMembers  聊天室最大成员数
     * @param custom  聊天室扩展信息，例如可以给聊天室添加业务相关的标记
     * @return 聊天室id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%9B%E5%BB%BA%E8%81%8A%E5%A4%A9%E5%AE%A4">创建聊天室</a>
     */
    public Mono<String> createRoom(String name, String description, String owner,
            List<String> members, int maxMembers, String custom) {
        return this.createRoom.createRoom(name, description, owner, members, maxMembers, custom);
    }

    /**
     * 创建聊天室。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * List<String> members = new ArrayList<>();
     * members.add("userA");
     *
     * try {
     *     String roomId = service.room().createRoom("name", "description", "owner", members, 200, "custom", true).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param name        聊天室名称
     * @param description 聊天室描述
     * @param owner       聊天室主
     * @param members     聊天室初始成员的用户名列表
     * @param maxMembers  聊天室最大成员数
     * @param custom  聊天室扩展信息，例如可以给聊天室添加业务相关的标记
     * @param needVerify 是否审核聊天室名称（付费功能，需联系商务开通）
     * @return 聊天室id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%9B%E5%BB%BA%E8%81%8A%E5%A4%A9%E5%AE%A4">创建聊天室</a>
     */
    public Mono<String> createRoom(String name, String description, String owner,
            List<String> members, int maxMembers, String custom, Boolean needVerify) {
        return this.createRoom.createRoom(name, description, owner, members, maxMembers, custom, needVerify);
    }

    /**
     * 获取聊天室详情。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     EMRoom room = service.room().getRoom(roomId).block();
     *     String roomName = room.name();
     *     String roomDescription = room.description();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param id 聊天室id
     * @return 聊天室详情或错误.
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E8%AF%A6%E6%83%85">获取聊天室详情</a>
     */
    public Mono<EMRoom> getRoom(String id) {
        return this.getRoomDetail.byId(id);
    }

    /**
     * 获取多个聊天室详情。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> roomIdList = new ArrayList<>();
     *     roomIdList.add("193100825821185");
     *     roomIdList.add("193100825821186");
     *
     *     List<EMRoom> roomList = service.group().getRoomList(roomIdList).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomIdList 聊天室id列表
     * @return 聊天室情或错误
     * @see <a href="http://docs-im-beta.easemob.com/document/server-side/chatroom.html#%E6%9F%A5%E8%AF%A2%E8%81%8A%E5%A4%A9%E5%AE%A4%E8%AF%A6%E6%83%85">获取聊天室详情</a>
     */
    public Mono<List<EMRoom>> getRoomList(List<String> roomIdList) {
        return this.getRoomDetail.byIds(roomIdList);
    }

    /**
     * 修改聊天室。
     * <p>
     * 可修改的字段参考 {@link com.easemob.im.server.api.room.update.UpdateRoomRequest UpdateRoomRequest}
     * <p>
     * API使用示例：
     * <p>
     * 比如，要更新聊天室名称，可以这么做:
     * <pre>{@code
     * EMService service;
     * try {
     *     service.updateRoom(roomId, request -> request.withName("some cool name")).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param id         聊天室id
     * @param customizer 更新请求定制函数
     * @return 成功或错误
     * @see com.easemob.im.server.api.room.update.UpdateRoomRequest
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E4%BF%AE%E6%94%B9%E8%81%8A%E5%A4%A9%E5%AE%A4%E4%BF%A1%E6%81%AF">修改聊天室</a>
     */
    public Mono<Void> updateRoom(String id, Consumer<UpdateRoomRequest> customizer) {
        return this.updateRoom.byId(id, customizer);
    }

    /**
     * 获取全部聊天室列表
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> rooms = service.room().listRoomsAll().collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @return 每个聊天室的id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96_app_%E4%B8%AD%E6%89%80%E6%9C%89%E7%9A%84%E8%81%8A%E5%A4%A9%E5%AE%A4">获取聊天室列表</a>
     */
    public Flux<String> listRoomsAll() {
        return this.listRooms.all(10);
    }

    /**
     * 分页获取聊天室列表
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * EMPage<String> page = null;
     * try {
     *     service.room().listRooms(10, null).block();
     *     List<String> roomIds = page.getValues();
     *     System.out.println("聊天室列表:" + roomIds);
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     *
     * // ... do something with the roomIds ...
     * if (page != null) {
     *      String cursor = page.getCursor();
     *      // cursor == null indicates the end of the list
     *      while (cursor != null) {
     *              try {
     *                  page = service.room().listRooms(10, cursor).block();
     *                  System.out.println("聊天室列表:" + page.getValues());
     *                  // ... do something to the roomIds ...
     *                  cursor = page.getCursor();
     *              } catch (EMException e) {
     *                  e.getErrorCode();
     *                  e.getMessage();
     *              }
     *      }
     * }
     * }</pre>
     *
     * @param limit  返回多少个聊天室id
     * @param cursor 开始位置
     * @return 获取聊天室响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96_app_%E4%B8%AD%E6%89%80%E6%9C%89%E7%9A%84%E8%81%8A%E5%A4%A9%E5%AE%A4">获取聊天室列表</a>
     */
    public Mono<EMPage<String>> listRooms(int limit, String cursor) {
        return this.listRooms.next(limit, cursor);
    }

    /**
     * 获取用户加入的聊天室列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> rooms = service.room().listRoomsUserJoined("username").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 用户名
     * @return 每个聊天室的id或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%8A%A0%E5%85%A5%E7%9A%84%E8%81%8A%E5%A4%A9%E5%AE%A4">获取用户加入的聊天室</a>
     */
    public Flux<String> listRoomsUserJoined(String username) {
        return this.listRooms.userJoined(username);
    }

    /**
     * 获取聊天室全部成员列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> members = service.room().listRoomMembersAll("roomId").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId 聊天室id
     * @return 每个聊天室成员或者错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">获取聊天室成员</a>
     */
    public Flux<String> listRoomMembersAll(String roomId) {
        return this.listRoomMembers.all(roomId, 10, null);
    }

    /**
     * 获取聊天室全部成员列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> members = service.room().listRoomMembersAll("roomId", "asc").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId 聊天室id
     * @param sort 聊天室成员排序方法 asc:根据加入顺序升序排序  desc:根据加入顺序降序排序
     * @return 每个聊天室成员或者错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">获取聊天室成员</a>
     */
    public Flux<String> listRoomMembersAll(String roomId, String sort) {
        return this.listRoomMembers.all(roomId, 10, sort);
    }

    /**
     * 获取聊天室全部成员列表，包括聊天室的 Owner。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<Map<String, String>> members = service.room().listRoomMembersAllWithOwner("roomId").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId 聊天室id
     * @return 每个聊天室成员或者错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">获取聊天室成员</a>
     */
    public Flux<Map<String, String>> listRoomMembersAllWithOwner(String roomId) {
        return this.listRoomMembers.all(roomId, 10);
    }

    /**
     * 分页获取聊天室成员列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * EMPage<String> page = null;
     * try {
     *     EMPage<String> page = service.room().listRoomMembers(roomId, 1, null).block();
     *     List<String> members = page.getValues();
     *     System.out.println("聊天室成员列表:" + members);
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     *
     * // ... do something with the roomIds ...
     * if (page != null) {
     *      String cursor = page.getCursor();
     *      // cursor == null indicates the end of the list
     *      while (cursor != null) {
     *              try {
     *                  page = service.room().listRoomMembers(roomId, 1, cursor).block();
     *                  System.out.println("聊天室成员列表:" + page.getValues());
     *                  // ... do something to the members ...
     *                  cursor = page.getCursor();
     *              } catch (EMException e) {
     *                  e.getErrorCode();
     *                  e.getMessage();
     *              }
     *      }
     * }
     * }</pre>
     *
     * @param roomId 聊天室id
     * @param limit  返回多少个聊天室成员
     * @param cursor 开始位置
     * @return 获取聊天室成员响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">获取聊天室成员</a>
     */
    public Mono<EMPage<String>> listRoomMembers(String roomId, int limit, String cursor) {
        return this.listRoomMembers.next(roomId, limit, cursor, null);
    }

    /**
     * 分页获取聊天室成员列表，包括聊天室的 Owner。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * EMPage<String> page = null;
     * try {
     *     List<Map<String, String>> members = service.room().listRoomMembers(roomId, 1, 10).block();
     *     System.out.println("聊天室成员列表:" + members);
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     *}</pre>
     *
     * @param roomId 聊天室id
     * @param pageNum  当前页码。默认从第 1 页开始获取
     * @param pageSize 每页期望返回的群组成员数量。取值范围为[1,100]。默认为 10。
     * @return 获取聊天室成员响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">获取聊天室成员</a>
     */
    public Mono<List<Map<String, String>>> listRoomMembersWithOwner(String roomId, int pageNum, int pageSize) {
        return this.listRoomMembers.next(roomId, pageNum, pageSize).map(
                ListRoomMembersResponseV1::getMembers);
    }

    /**
     * 分页获取聊天室成员列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * EMPage<String> page = null;
     * try {
     *     EMPage<String> page = service.room().listRoomMembers(roomId, 1, null, "asc").block();
     *     List<String> members = page.getValues();
     *     System.out.println("聊天室成员列表:" + members);
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     *
     * // ... do something with the roomIds ...
     * if (page != null) {
     *      String cursor = page.getCursor();
     *      // cursor == null indicates the end of the list
     *      while (cursor != null) {
     *              try {
     *                  page = service.room().listRoomMembers(roomId, 1, cursor, "asc").block();
     *                  System.out.println("聊天室成员列表:" + page.getValues());
     *                  // ... do something to the members ...
     *                  cursor = page.getCursor();
     *              } catch (EMException e) {
     *                  e.getErrorCode();
     *                  e.getMessage();
     *              }
     *      }
     * }
     * }</pre>
     *
     * @param roomId 聊天室id
     * @param limit  返回多少个聊天室成员
     * @param cursor 开始位置
     * @param sort 聊天室成员排序方法 asc:根据加入顺序升序排序  desc:根据加入顺序降序排序
     * @return 获取聊天室成员响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">获取聊天室成员</a>
     */
    public Mono<EMPage<String>> listRoomMembers(String roomId, int limit, String cursor, String sort) {
        return this.listRoomMembers.next(roomId, limit, cursor, sort);
    }

    /**m
     * 向聊天室添加成员。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.room().addRoomMember("roomId", "username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId   聊天室id
     * @param username 要添加的用户的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E5%8D%95%E4%B8%AA%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">聊天室添加成员</a>
     */
    public Mono<Void> addRoomMember(String roomId, String username) {
        return this.addRoomMember.single(roomId, username);
    }

    /**
     * 从聊天室移除成员。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.room().removeRoomMember("roomId", "username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId   聊天室id
     * @param username 要移除的成员的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%A0%E9%99%A4%E5%8D%95%E4%B8%AA%E8%81%8A%E5%A4%A9%E5%AE%A4%E6%88%90%E5%91%98">聊天室移除成员</a>
     */
    public Mono<Void> removeRoomMember(String roomId, String username) {
        return this.removeRoomMember.single(roomId, username);
    }

    /**
     * 获取聊天室管理员。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> admins = service.room().listRoomAdminsAll("roomId").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId 聊天室id
     * @return 每个管理员的用户名或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98%E5%88%97%E8%A1%A8">获取聊天室管理员</a>
     */
    public Flux<String> listRoomAdminsAll(String roomId) {
        return this.listRoomAdmins.all(roomId);
    }

    /**
     * 升级聊天室成员至管理员。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.room().promoteRoomAdmin("roomId", "username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId   聊天室id
     * @param username 要升级的成员的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98">添加聊天室管理员</a>
     */
    public Mono<Void> promoteRoomAdmin(String roomId, String username) {
        return this.promoteRoomAdmin.single(roomId, username);
    }

    /**
     * 降级聊天室管理员至成员。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.room().demoteRoomAdmin("roomId", "username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId   聊天室id
     * @param username 要降级的管理员的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E7%A7%BB%E9%99%A4%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%AE%A1%E7%90%86%E5%91%98">移除聊天室管理员</a>
     */
    public Mono<Void> demoteRoomAdmin(String roomId, String username) {
        return this.demoteRoomAdmin.single(roomId, username);
    }

    /**
     * 获取所有超级管理员列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> superAdmins = service.room().listRoomSuperAdminsAll().collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @return 所有超级管理员的用户名
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E8%81%8A%E5%A4%A9%E5%AE%A4%E8%B6%85%E7%BA%A7%E7%AE%A1%E7%90%86%E5%91%98%E5%88%97%E8%A1%A8">分页获取聊天室超级管理员列表</a>
     */
    public Flux<String> listRoomSuperAdminsAll() {
        return this.listRoomSuperAdmins.all(1);
    }

    /**
     * 升级用户为超级管理员，只有超级管理员有权限创建聊天室。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.room().promoteRoomSuperAdmin("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要升级的用户的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E6%B7%BB%E5%8A%A0%E8%B6%85%E7%BA%A7%E7%AE%A1%E7%90%86%E5%91%98">添加超级管理员</a>
     */
    public Mono<Void> promoteRoomSuperAdmin(String username) {
        return this.promoteRoomSuperAdmin.single(username);
    }

    /**
     * 降级超级管理员为普通用户
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.room().demoteRoomSuperAdmin("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要降级的超级管理员的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E7%A7%BB%E9%99%A4%E8%B6%85%E7%BA%A7%E7%AE%A1%E7%90%86%E5%91%98">移除超级管理员</a>
     */
    public Mono<Void> demoteRoomSuperAdmin(String username) {
        return this.demoteRoomSuperAdmin.single(username);
    }

    /**
     * 注销聊天室
     * <p>
     * 请谨慎使用。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.room().destroyRoom("roomId").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param roomId 聊天室id
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatroom#%E5%88%A0%E9%99%A4%E8%81%8A%E5%A4%A9%E5%AE%A4">删除聊天室</a>
     */
    public Mono<Void> destroyRoom(String roomId) {
        return this.deleteRoom.byId(roomId);
    }

    /**
     * 转让聊天室。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.room().assignRoom("chatroomId", "newOwner").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param chatroomId  聊天室id
     * @param newOwner 被转让聊天室的用户名
     * @return 成功或错误
     */
    public Mono<Void> assignRoom(String chatroomId, String newOwner){
        return this.assignRoom.execute(chatroomId, newOwner);
    }

    /**
     * 获取聊天室公告。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     String roomAnnouncement = service.room().getRoomAnnouncement("roomId").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param chatroomId 聊天室id
     * @return 聊天室公告或错误
     */
    public Mono<String> getRoomAnnouncement(String chatroomId) {
        return this.roomAnnouncement.get(chatroomId);
    }

    /**
     * 更新聊天室公告。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.room().updateRoomAnnouncement("chatroomId", "announcement").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param chatroomId   聊天室id
     * @param announcement 聊天室公告
     * @return 成功或错误
     */
    public Mono<Void> updateRoomAnnouncement(String chatroomId, String announcement) {
        return this.roomAnnouncement.set(chatroomId, announcement);
    }

}
