package com.easemob.im.server.api.metadata;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.metadata.chatroom.AutoDelete;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.model.EMMetadata;
import com.easemob.im.server.model.EMMetadataBatch;
import com.easemob.im.server.model.EMPage;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetadataIT extends AbstractIT {
    public MetadataIT() {
        super();
    }

    @Test
    public void testMetadataSet() {

        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testMetadataGet() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.metadata().getMetadataFromUser(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testMetadataBatchGet() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        String randomUsername = Utilities.randomUserName();
        String randomUsername1 = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername1, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername1, map)
                .block(Utilities.IT_TIMEOUT));

        List<String> targets = new ArrayList<>();
        targets.add(randomUsername);
        targets.add(randomUsername1);

        List<String> properties = new ArrayList<>();
        properties.add("nickname");
        properties.add("avatar");
        properties.add("phone");

        assertDoesNotThrow(() -> this.service.metadata().getMetadataFromUsers(targets, properties)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername1).block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testMetadataGetUsage() {
        assertDoesNotThrow(() -> this.service.metadata().getUsage().block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testMetadataDelete() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.metadata().getMetadataFromUser(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.metadata().deleteMetadataFromUser(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testChatRoomMetadataSet() {

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
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200,
                        "custom")
                .block(Utilities.IT_TIMEOUT));

        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        assertDoesNotThrow(() -> this.service.metadata()
                .setChatRoomMetadata(randomMemberUsername, roomId, map,
                        AutoDelete.DELETE)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.metadata()
                .setChatRoomMetadataForced(randomOwnerUsername, roomId, map,
                        AutoDelete.DELETE)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.metadata().listChatRoomMetadataAll(roomId)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testChatRoomMetadataGet() {

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
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200,
                        "custom")
                .block(Utilities.IT_TIMEOUT));

        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        assertDoesNotThrow(() -> this.service.metadata()
                .setChatRoomMetadata(randomMemberUsername, roomId, map,
                        AutoDelete.DELETE)
                .block(Utilities.IT_TIMEOUT));

        List<String> keys = new ArrayList<>();
        keys.add("nickname");

        assertDoesNotThrow(() -> this.service.metadata().listChatRoomMetadata(roomId, keys)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.metadata().listChatRoomMetadataAll(roomId)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testChatRoomMetadataDelete() {

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
                .createRoom("chat room", "room description", randomOwnerUsername, members, 200,
                        "custom")
                .block(Utilities.IT_TIMEOUT));

        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        assertDoesNotThrow(() -> this.service.metadata()
                .setChatRoomMetadata(randomMemberUsername, roomId, map,
                        AutoDelete.DELETE)
                .block(Utilities.IT_TIMEOUT));

        List<String> keys = new ArrayList<>();
        keys.add("nickname");

        assertDoesNotThrow(() -> this.service.metadata().listChatRoomMetadata(roomId, keys)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.metadata()
                .deleteChatRoomMetadata(randomMemberUsername, roomId, keys)
                .block(Utilities.IT_TIMEOUT));

        keys.clear();
        keys.add("avatar");

        assertDoesNotThrow(() -> this.service.metadata().deleteChatRoomMetadataForced(roomId, keys)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.metadata().listChatRoomMetadataAll(roomId)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testChatGroupMetadataUserSetAndGet() {

        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPublicGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));

        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        assertDoesNotThrow(() -> this.service.metadata().setMetadataToChatGroupUser(randomMemberUsername, groupId, map)
                .block(Utilities.IT_TIMEOUT));

        EMMetadata metadata = assertDoesNotThrow(() -> this.service.metadata().getMetadataFromChatGroupUser(randomMemberUsername, groupId)
                .block(Utilities.IT_TIMEOUT));

        System.out.println("metadata : " + metadata);

        assertEquals("昵称", metadata.getData().get("nickname"));
        assertEquals("http://www.easemob.com/avatar.png", metadata.getData().get("avatar"));
        assertEquals("159", metadata.getData().get("phone"));

        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void testChatGroupUsersMetadataBatchGet() {

        String randomOwnerUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomMemberUsername = Utilities.randomUserName();
        List<String> members = new ArrayList<>();
        members.add(randomMemberUsername);
        assertDoesNotThrow(() -> this.service.user().create(randomOwnerUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomMemberUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String groupId = assertDoesNotThrow(() -> this.service.group()
                .createPublicGroup(randomOwnerUsername, "group", "group description", members, 200,
                        true).block(Utilities.IT_TIMEOUT));

        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        assertDoesNotThrow(() -> this.service.metadata().setMetadataToChatGroupUser(randomOwnerUsername, groupId, map)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.metadata().setMetadataToChatGroupUser(randomMemberUsername, groupId, map)
                .block(Utilities.IT_TIMEOUT));

        List<String> targets = new ArrayList<>();
        targets.add(randomOwnerUsername);
        targets.add(randomMemberUsername);

        List<String> properties = new ArrayList<>();
        properties.add("nickname");
        properties.add("avatar");
        properties.add("phone");

        EMMetadataBatch metadataBatch = assertDoesNotThrow(() -> this.service.metadata().getMetadataFromChatGroupUsers(groupId, targets, properties)
                .block(Utilities.IT_TIMEOUT));

        System.out.println("metadataBatch : " + metadataBatch);

        assertEquals("昵称", metadataBatch.getData().get(randomOwnerUsername).get("nickname"));
        assertEquals("http://www.easemob.com/avatar.png", metadataBatch.getData().get(randomOwnerUsername).get("avatar"));
        assertEquals("159", metadataBatch.getData().get(randomOwnerUsername).get("phone"));

        assertEquals("昵称", metadataBatch.getData().get(randomMemberUsername).get("nickname"));
        assertEquals("http://www.easemob.com/avatar.png", metadataBatch.getData().get(randomMemberUsername).get("avatar"));
        assertEquals("159", metadataBatch.getData().get(randomMemberUsername).get("phone"));

        assertDoesNotThrow(
                () -> this.service.group().destroyGroup(groupId).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomOwnerUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomMemberUsername)
                .block(Utilities.IT_TIMEOUT));
    }


}
