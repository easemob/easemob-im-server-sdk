package com.easemob.im.server.api.metadata;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.metadata.chatroom.AutoDelete;
import com.easemob.im.server.api.util.Utilities;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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


}
