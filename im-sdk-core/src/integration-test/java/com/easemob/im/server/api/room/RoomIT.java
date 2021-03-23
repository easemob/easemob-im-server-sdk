package com.easemob.im.server.api.room;

import com.easemob.im.server.api.AbstractIT;
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
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(3)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testRoomGet() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(3)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().getRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testRoomUpdate() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(3)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().updateRoom(roomId, settings -> settings.withMaxMembers(100)).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testRoomListAll() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(5)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().listRoomsAll().blockFirst(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(5)));
    }

    @Test
    void testRoomListRooms() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(5)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().listRooms(1, null).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(5)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(5)));
    }

    @Test
    void testRoomUserJoinedList() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(3)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().listRoomsUserJoined(randomOwnerUsername).blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testRoomMembersAll() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(3)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        // 分页获取有问题
        assertDoesNotThrow(() -> this.service.room().listRoomMembersAll(roomId).blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testRoomMembers() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(3)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        // 分页获取有问题
        assertDoesNotThrow(() -> this.service.room().listRoomMembers(roomId, 1, null).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testRoomAddMember() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(3)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().addRoomMember(roomId, randomMemberUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testRoomRemoveMember() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(3)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().removeRoomMember(roomId, randomMemberUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testRoomAdminsAll() {
        String randomOwnerUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomOwnerUsername;

        String randomMemberUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword).block(Duration.ofSeconds(3)));
        String roomId = assertDoesNotThrow(() -> this.service.room().createRoom("chat room", "room description", randomOwnerUsername, members, 200).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().promoteRoomAdmin(roomId, randomMemberUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().listRoomAdminsAll(roomId).blockFirst(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.room().destroyRoom(roomId).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomOwnerUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername).block(Duration.ofSeconds(3)));
    }


}
