package com.easemob.im.server.api.chatrooms;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.model.ChatRoom;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ChatRoomsApiTest {

    @Test
    public void getAppAllChatRoom() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().getAppAllChatRoom();
        System.out.println("result " + result);
    }

    @Test
    public void getUserJoinedChatRoom() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().getUserJoinedChatRoom("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomDetails() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomDetails("138135017160705");
        System.out.println("result " + result);
    }

    @Test
    public void testGetChatRoomDetails() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> roomIds = new HashSet<>();
        roomIds.add("138135017160705");

        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomDetails(roomIds);
        System.out.println("result " + result);
    }

    @Test
    public void createChatRoom() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        // 137498934181889 137498964590593
        String result = EMClient.getInstance().chatRooms()
                .createChatRoom("testChatroom", "description", 3, "testuser0001", null);
        System.out.println("result " + result);
    }

    @Test
    public void modifyChatRoomInfo() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().modifyChatRoomInfo("138135017160705", "modifyRoomName", "modifyDescription", 10);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatRoom() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().deleteChatRoom("138135260430337");
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomMembers() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomMembers("138135017160705", 1, 10);
        System.out.println("result " + result);
    }

    @Test
    public void addChatRoomMember() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().addChatRoomMember("138135017160705", "testuser0002");
        System.out.println("result " + result);
    }

    @Test
    public void batchAddChatRoomMember() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        ChatRoom result = EMClient.getInstance().chatRooms().batchAddChatRoomMember("138135017160705", members);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatRoomMember() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().deleteChatRoomMember("138135017160705", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void batchDeleteChatRoomMember() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        ChatRoom result = EMClient.getInstance().chatRooms().batchDeleteChatRoomMember("138135017160705", members);
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomAdminList() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomAdminList("138135017160705");
        System.out.println("result " + result);
    }

    @Test
    public void addChatGroupAdmin() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().addChatGroupAdmin("138135017160705", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void removeChatRoomAdmin() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().removeChatRoomAdmin("138135017160705", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void addMute() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().addMute("138135017160705", "testuser0005", 10000L);
        System.out.println("result " + result);
    }

    @Test
    public void testAddMute() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        ChatRoom result = EMClient.getInstance().chatRooms().addMute("138135017160705", members, 100000L);
        System.out.println("result " + result);
    }

    @Test
    public void removeMute() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().removeMute("138135017160705", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void testRemoveMute() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0005");

        ChatRoom result = EMClient.getInstance().chatRooms().removeMute("138135017160705", members);
        System.out.println("result " + result);
    }

    @Test
    public void getMuteList() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().getMuteList("138135017160705");
        System.out.println("result " + result);
    }

    @Test
    public void getChatRoomSuperAdminList() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().getChatRoomSuperAdminList(1, 10);
        System.out.println("result " + result);
    }

    @Test
    public void addChatRoomSuperAdmin() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().addChatRoomSuperAdmin("testuser0008");
        System.out.println("result " + result);
    }

    @Test
    public void removeChatRoomSuperAdmin() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        ChatRoom result = EMClient.getInstance().chatRooms().removeChatRoomSuperAdmin("testuser0008");
        System.out.println("result " + result);
    }
}