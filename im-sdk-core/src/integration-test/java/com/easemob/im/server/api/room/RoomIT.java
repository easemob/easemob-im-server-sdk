package com.easemob.im.server.api.room;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.model.EMBlock;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static com.easemob.im.server.utils.RandomMaker.makeRandomUserName;

public class RoomIT extends AbstractIT {

    RoomIT() {
        super();
    }

    @Test
    void testRoomCreate() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomGet() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().getRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomUpdate() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room()
                .updateRoom(roomId, settings -> settings.withMaxMembers(100))
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomListAll() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().listRoomsAll().blockFirst(Duration.ofSeconds(5)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(5)));
    }

    @Test
    void testRoomListRooms() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().listRooms(1, null).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(5)));
    }

    @Test
    void testRoomUserJoinedList() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().listRoomsUserJoined(randomOwnerUsername)
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomMembersAll() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().listRoomMembersAll(roomId)
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomMembers() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().listRoomMembers(roomId, 1, null)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomAddMember() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().addRoomMember(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomRemoveMember() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().removeRoomMember(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomAdminsAll() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().listRoomAdminsAll(roomId)
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomPromoteAdmin() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomDemoteAdmin() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().demoteRoomAdmin(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomSuperAdminsAll() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomSuperAdmin(randomUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().listRoomSuperAdminsAll()
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomPromoteSuperAdmin() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomSuperAdmin(randomUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomDemoteSuperAdmin() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().demoteRoomSuperAdmin(randomUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomDestroy() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomUsersBlockedSendMsg() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToRoom(randomMemberUsername, roomId, Duration.ofMillis(6000))
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block().listUsersBlockedSendMsgToRoom(roomId)
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomBlockUserSendMsg() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToRoom(randomMemberUsername, roomId, Duration.ofMillis(30000))
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().removeRoomMember(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.room().addRoomMember(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(10)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().listUsersBlockedSendMsgToRoom(roomId)
                        .blockFirst(Duration.ofSeconds(3)));
        if (!block.getUsername().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s room mute list", randomMemberUsername,
                            roomId));
        }
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testRoomUnblockUserSendMsg() {
        String randomOwnerUsername = makeRandomUserName();
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = makeRandomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToRoom(randomMemberUsername, roomId, Duration.ofMillis(6000))
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.block().unblockUserSendMsgToRoom(randomMemberUsername, roomId)
                        .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(10)));
    }

}
