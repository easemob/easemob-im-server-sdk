package com.easemob.im.server.api.room;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.model.EMBlock;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RoomIT extends AbstractIT {

    RoomIT() {
        super();
    }

    @Test
    void testRoomCreate() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomGet() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().getRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomUpdate() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room()
                .updateRoom(roomId, settings -> settings.withMaxMembers(100))
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomListAll() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().listRoomsAll().blockFirst(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomListRooms() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().listRooms(1, null).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomUserJoinedList() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().listRoomsUserJoined(randomOwnerUsername)
                .blockFirst(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomMembersAll() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().listRoomMembersAll(roomId)
                .blockFirst(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomMembers() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().listRoomMembers(roomId, 1, null)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomAddMember() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().addRoomMember(roomId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomRemoveMember() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().removeRoomMember(roomId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomAdminsAll() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().listRoomAdminsAll(roomId)
                .blockFirst(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomPromoteAdmin() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomDemoteAdmin() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().demoteRoomAdmin(roomId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomSuperAdminsAll() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().promoteRoomSuperAdmin(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().listRoomSuperAdminsAll()
                .blockFirst(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomPromoteSuperAdmin() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().promoteRoomSuperAdmin(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomDemoteSuperAdmin() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().demoteRoomSuperAdmin(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomDestroy() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomUsersBlockedSendMsg() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToRoom(randomMemberUsername, roomId, Duration.ofMillis(6000))
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block().listUsersBlockedSendMsgToRoom(roomId)
                .blockFirst(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomBlockUserSendMsg() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToRoom(randomMemberUsername, roomId, Duration.ofMillis(30000))
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().removeRoomMember(roomId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.room().addRoomMember(roomId, randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().listUsersBlockedSendMsgToRoom(roomId)
                        .blockFirst(Utilities.IT_TIMEOUT));
        if (!block.getUsername().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s room mute list", randomMemberUsername,
                            roomId));
        }
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRoomUnblockUserSendMsg() {
        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToRoom(randomMemberUsername, roomId, Duration.ofMillis(6000))
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.block().unblockUserSendMsgToRoom(randomMemberUsername, roomId)
                        .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

}
