package com.easemob.im.server.api.chatrooms;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.chatrooms.create.CreateRoom;
import com.easemob.im.server.api.chatrooms.detail.GetRoomDetail;
import com.easemob.im.server.api.chatrooms.list.ListRooms;
import com.easemob.im.server.api.chatrooms.list.ListRoomsResponse;
import com.easemob.im.server.api.chatrooms.update.UpdateRoom;
import com.easemob.im.server.api.chatrooms.update.UpdateRoomRequest;
import com.easemob.im.server.model.EMRoom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RoomApi {
    private static final List<String> EMPTY_MEMBER_LIST = new ArrayList<>();

    private static final int DEFAULT_MAX_MEMBERS = 200;

    private Context context;

    /**
     * Create a room.
     *
     * @param name the room's name
     * @param description the room's description
     * @param owner the owner's username
     * @return A {@code Mono} which emits {@code EMRoom}.
     */
    public Mono<String> createRoom(String name, String description, String owner) {
        return CreateRoom.createRoom(this.context, name, description, owner, EMPTY_MEMBER_LIST, DEFAULT_MAX_MEMBERS);
    }

    /**
     * Create a room.
     *
     * @param name the room's name
     * @param description the room's description
     * @param owner the owner's username
     * @param members the rooms members
     * @param maxMembers max members count
     * @return A {@code Mono} which emits {@code EMRoom}.
     */
    public Mono<String> createRoom(String name, String description, String owner, List<String> members, int maxMembers) {
        return CreateRoom.createRoom(this.context, name, description, owner, members, maxMembers);
    }

    /**
     * Get the room's detail by id.
     *
     * @param id the room's id
     * @return A {@code Mono} which emits {@code EMRoomDetail}.
     */
    public Mono<EMRoom> getRoom(String id) {
        return GetRoomDetail.byId(this.context, id);
    }


    /**
     * Update the room.
     *
     * Currently, you can update:
     * - name
     * - description
     * - maxMembers
     * More fields will be added.
     *
     * To update room's name, you can:
     * <pre>{@code
     *  EMService service;
     *  service.updateRoom(roomId, request -> request.withName("some cool name")).block(timeout);
     * }</pre>
     *
     * @param id the room's id
     * @param customizer the update request customizer
     * @return A {@code Mono} which complete upon success.
     */
    public Mono<Void> updateRoom(String id, Consumer<UpdateRoomRequest> customizer) {
        return UpdateRoom.byId(this.context, id, customizer);
    }

    /**
     * List all rooms.
     *
     * @return A {@code Flux} which emits each room's id.
     */
    public Flux<String> listRooms() {
        return ListRooms.all(this.context, 10);
    }

    /**
     * List rooms iteratively.
     *
     * @param limit how many results returns, 10 is a good starting point
     * @param cursor where to continue, returned in previous response.
     *               For the first call, pass {@code null}.
     * @return A {@code Mono} which emits {@code ListRoomsResponse} upon success.
     */
    public Mono<ListRoomsResponse> listRoomsPaged(int limit, String cursor) {
        return ListRooms.next(this.context, limit, cursor);
    }

    /**
     * List rooms user joined.
     *
     * @param username the user's username
     * @return A {@code Flux} of each room's id.
     */
    public Flux<String> listRoomsUserJoined(String username) {
        return ListRooms.userJoined(this.context, username);
    }
}
