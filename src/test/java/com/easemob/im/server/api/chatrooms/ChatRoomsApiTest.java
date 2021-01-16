package com.easemob.im.server.api.chatrooms;

import com.easemob.im.server.EMClient;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ChatRoomsApiTest {

    @Test
    public void getAppAllChatRoom() {
        JsonNode result = EMClient.getInstance().chatRooms().getAppAllChatRoom();
        System.out.println("result " + result);
    }

    @Test
    public void getUserJoinedChatRoom() {
        JsonNode result = EMClient.getInstance().chatRooms().getUserJoinedChatRoom("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomDetails() {
        JsonNode result = EMClient.getInstance().chatRooms().getChatRoomDetails("137498934181889");
        System.out.println("result " + result);
    }

    @Test
    public void testGetChatRoomDetails() {
        Set<String> roomIds = new HashSet<>();
        roomIds.add("137498934181889");
        roomIds.add("137498964590593");

        JsonNode result = EMClient.getInstance().chatRooms().getChatRoomDetails(roomIds);
        System.out.println("result " + result);
    }

    @Test
    public void createChatRoom() {
        // 137498934181889 137498964590593
        JsonNode result = EMClient.getInstance()
                .chatRooms()
                .createChatRoom("testChatroom", "description", 3, "testuser0001", null);
        System.out.println("result " + result);
    }

    @Test
    public void modifyChatRoomInfo() {
        JsonNode result = EMClient.getInstance().chatRooms().modifyChatRoomInfo("137498964590593", "modifyRoomName", "modifyDescription", 10);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatRoom() {
        JsonNode result = EMClient.getInstance().chatRooms().deleteChatRoom("137498934181889");
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomMembers() {
        JsonNode result = EMClient.getInstance().chatRooms().getChatRoomMembers("137498964590593", 1, 10);
        System.out.println("result " + result);
    }

    @Test
    public void addChatRoomMember() {
        JsonNode result = EMClient.getInstance().chatRooms().addChatRoomMember("137498964590593", "testuser0002");
        System.out.println("result " + result);
    }

    @Test
    public void batchAddChatRoomMember() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        JsonNode result = EMClient.getInstance().chatRooms().batchAddChatRoomMember("137498964590593", members);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatRoomMember() {
        JsonNode result = EMClient.getInstance().chatRooms().deleteChatRoomMember("137498964590593", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void batchDeleteChatRoomMember() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        JsonNode result = EMClient.getInstance().chatRooms().batchDeleteChatRoomMember("137498964590593", members);
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomAdminList() {
        JsonNode result = EMClient.getInstance().chatRooms().getChatRoomAdminList("137498964590593");
        System.out.println("result " + result);
    }

    @Test
    public void addChatGroupAdmin() {
        JsonNode result = EMClient.getInstance().chatRooms().addChatGroupAdmin("137498964590593", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void removeChatRoomAdmin() {
        JsonNode result = EMClient.getInstance().chatRooms().removeChatRoomAdmin("137498964590593", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void addMute() {
        JsonNode result = EMClient.getInstance().chatRooms().addMute("137498964590593", "testuser0005", 1000L);
        System.out.println("result " + result);
    }

    @Test
    public void testAddMute() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        JsonNode result = EMClient.getInstance().chatRooms().addMute("137498964590593", members, 1000L);
        System.out.println("result " + result);
    }

    @Test
    public void removeMute() {
        JsonNode result = EMClient.getInstance().chatRooms().removeMute("137498964590593", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void testRemoveMute() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        JsonNode result = EMClient.getInstance().chatRooms().removeMute("137498964590593", members);
        System.out.println("result " + result);
    }

    @Test
    public void getMuteList() {
        JsonNode result = EMClient.getInstance().chatRooms().getMuteList("137498964590593");
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomSuperAdminList() {
        JsonNode result = EMClient.getInstance().chatRooms().getChatRoomSuperAdminList(1, 10);
        System.out.println("result " + result);
    }

    @Test
    public void addChatRoomSuperAdmin() {
        JsonNode result = EMClient.getInstance().chatRooms().addChatRoomSuperAdmin("testuser0008");
        System.out.println("result " + result);
    }

    @Test
    public void removeChatRoomSuperAdmin() {
        JsonNode result = EMClient.getInstance().chatRooms().removeChatRoomSuperAdmin("testuser0008");
        System.out.println("result " + result);
    }
}