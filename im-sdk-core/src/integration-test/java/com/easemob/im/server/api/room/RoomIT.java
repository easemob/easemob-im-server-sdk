package com.easemob.im.server.api.room;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.model.EMBlock;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RoomIT extends AbstractIT {

    RoomIT() {
        super();
    }

    @Test
    void testRoomCreate() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomGet() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().getRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomUpdate() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room()
                .updateRoom(roomId, settings -> settings.withMaxMembers(100))
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomListAll() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
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
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(5)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
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
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().listRoomsUserJoined(randomOwnerUsername)
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomMembersAll() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().listRoomMembersAll(roomId)
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomMembers() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().listRoomMembers(roomId, 1, null)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomAddMember() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().addRoomMember(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomRemoveMember() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().removeRoomMember(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomAdminsAll() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().listRoomAdminsAll(roomId)
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomPromoteAdmin() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomDemoteAdmin() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().demoteRoomAdmin(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomSuperAdminsAll() {
        String randomUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomSuperAdmin(randomUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().listRoomSuperAdminsAll()
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomPromoteSuperAdmin() {
        String randomUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomSuperAdmin(randomUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomDemoteSuperAdmin() {
        String randomUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().demoteRoomSuperAdmin(randomUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomDestroy() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomUsersBlockedSendMsg() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToRoom(randomMemberUsername, roomId, Duration.ofMillis(6000))
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.block().listUsersBlockedSendMsgToRoom(roomId)
                .blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomBlockUserSendMsg() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToRoom(randomMemberUsername, roomId, Duration.ofMillis(30000))
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().removeRoomMember(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.room().addRoomMember(roomId, randomMemberUsername)
                .block(Duration.ofSeconds(30)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().listUsersBlockedSendMsgToRoom(roomId)
                        .blockFirst(Duration.ofSeconds(3)));
        if (!block.getUsername().equals(randomMemberUsername)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s room mute list", randomMemberUsername,
                            roomId));
        }
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

    @Test
    void testRoomUnblockUserSendMsg() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        String roomId = assertDoesNotThrow(() -> this.service.room()
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToRoom(randomMemberUsername, roomId, Duration.ofMillis(6000))
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.block().unblockUserSendMsgToRoom(randomMemberUsername, roomId)
                        .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Duration.ofSeconds(30)));
    }

}
