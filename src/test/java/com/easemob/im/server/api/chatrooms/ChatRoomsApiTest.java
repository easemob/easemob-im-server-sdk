package com.easemob.im.server.api.chatrooms;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.model.ChatRoom;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ChatRoomsApiTest {

    @Test
    public void getAppAllChatRoom() {
        ChatRoom result = EMClient.getInstance().chatRooms().getAppAllChatRoom();
        System.out.println("result " + result);
    }

    @Test
    public void getUserJoinedChatRoom() {
        ChatRoom result = EMClient.getInstance().chatRooms().getUserJoinedChatRoom("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomDetails() {
        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomDetails("138135017160705");
        System.out.println("result " + result);
    }

    @Test
    public void testGetChatRoomDetails() {
        Set<String> roomIds = new HashSet<>();
        roomIds.add("138135017160705");

        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomDetails(roomIds);
        System.out.println("result " + result);
    }

    @Test
    public void createChatRoom() {
        // 137498934181889 137498964590593
        String result = EMClient.getInstance().chatRooms()
                .createChatRoom("testChatroom", "description", 3, "testuser0001", null);
        System.out.println("result " + result);
    }

    @Test
    public void modifyChatRoomInfo() {
        ChatRoom result = EMClient.getInstance().chatRooms().modifyChatRoomInfo("138135017160705", "modifyRoomName", "modifyDescription", 10);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatRoom() {
        ChatRoom result = EMClient.getInstance().chatRooms().deleteChatRoom("138135260430337");
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomMembers() {
        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomMembers("138135017160705", 1, 10);
        System.out.println("result " + result);
    }

    @Test
    public void addChatRoomMember() {
        ChatRoom result = EMClient.getInstance().chatRooms().addChatRoomMember("138135017160705", "testuser0002");
        System.out.println("result " + result);
    }

    @Test
    public void batchAddChatRoomMember() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        ChatRoom result = EMClient.getInstance().chatRooms().batchAddChatRoomMember("138135017160705", members);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatRoomMember() {
        ChatRoom result = EMClient.getInstance().chatRooms().deleteChatRoomMember("138135017160705", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void batchDeleteChatRoomMember() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        ChatRoom result = EMClient.getInstance().chatRooms().batchDeleteChatRoomMember("138135017160705", members);
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomAdminList() {
        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomAdminList("138135017160705");
        System.out.println("result " + result);
    }

    @Test
    public void addChatGroupAdmin() {
        ChatRoom result = EMClient.getInstance().chatRooms().addChatGroupAdmin("138135017160705", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void removeChatRoomAdmin() {
        ChatRoom result = EMClient.getInstance().chatRooms().removeChatRoomAdmin("138135017160705", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void addMute() {
        ChatRoom result = EMClient.getInstance().chatRooms().addMute("138135017160705", "testuser0005", 10000L);
        System.out.println("result " + result);
    }

    @Test
    public void testAddMute() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        ChatRoom result = EMClient.getInstance().chatRooms().addMute("138135017160705", members, 100000L);
        System.out.println("result " + result);
    }

    @Test
    public void removeMute() {
        ChatRoom result = EMClient.getInstance().chatRooms().removeMute("138135017160705", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void testRemoveMute() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        ChatRoom result = EMClient.getInstance().chatRooms().removeMute("138135017160705", members);
        System.out.println("result " + result);
    }

    @Test
    public void getMuteList() {
        ChatRoom result = EMClient.getInstance().chatRooms().getMuteList("138135017160705");
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomSuperAdminList() {
        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomSuperAdminList(1, 10);
        System.out.println("result " + result);
    }

    @Test
    public void addChatRoomSuperAdmin() {
        ChatRoom result = EMClient.getInstance().chatRooms().addChatRoomSuperAdmin("testuser0008");
        System.out.println("result " + result);
    }

    @Test
    public void removeChatRoomSuperAdmin() {
        ChatRoom result = EMClient.getInstance().chatRooms().removeChatRoomSuperAdmin("testuser0008");
        System.out.println("result " + result);
    }
}