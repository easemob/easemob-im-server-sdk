package com.easemob.im.server.api.chatgroups;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.model.ChatGroup;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class ChatGroupsApiTest {

    @Test
    public void getAppAllChatGroup() {
        ChatGroup result = EMClient.getInstance().chatGroups().getAppAllChatGroup(10, null);
        System.out.println("result " + result);
    }

    @Test
    public void getUserJoinAllChatGroup() {
        ChatGroup result = EMClient.getInstance().chatGroups().getUserJoinAllChatGroup("testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupDetails() {
        ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupDetails("137490869583873");
        System.out.println("result " + result);
    }

    @Test
    public void testGetChatGroupDetails() {
        // 137490750046209   137490869583873
        Set<String> groupIds = new HashSet<>();
//        members.add("137490750046209");
        groupIds.add("137490869583873");

        ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupDetails(groupIds);
        System.out.println("result " + result);
    }

    @Test
    public void createChatGroup() {
        Set<String> members = new HashSet<>();
        members.add("testuser0002");

        String result = EMClient.getInstance().chatGroups().createChatGroup("testChatGroup1", "test", true, 3, false, false, "testuser0001", members);
        System.out.println("result " + result);
    }

    @Test
    public void modifyChatGroupInfo() {
        ChatGroup result = EMClient.getInstance().chatGroups().modifyChatGroupInfo("137490869583873", "modifyGroupName", "modifyDescription", 5, null, null);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatGroup() {
        ChatGroup result = EMClient.getInstance().chatGroups().deleteChatGroup("138112814612481");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupAnnouncement() {
        ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupAnnouncement("137490869583873");
        System.out.println("result " + result);
    }

    @Test
    public void modifyChatGroupAnnouncement() {
        ChatGroup result = EMClient.getInstance().chatGroups().modifyChatGroupAnnouncement("137490869583873", "群组公告");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupShareFile() {
        ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupShareFile("137490869583873");
        System.out.println("result " + result);
    }

    @Test
    public void testGetChatGroupShareFile() {
        ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupShareFile("137490869583873", 1, 5);
        System.out.println("result " + result);
    }

    @Test
    public void uploadChatGroupShareFile() {
        File file = new File("/Users/easemob-dn0164/Desktop/9090.jpg");
        ChatGroup result = EMClient.getInstance().chatGroups().uploadChatGroupShareFile("137490869583873", file);
        System.out.println("result " + result);
    }

    @Test
    public void downloadChatGroupShareFile() {
        JsonNode result = EMClient.getInstance().chatGroups().downloadChatGroupShareFile("137490869583873", "eb9ae860-5acf-11eb-ad29-f3026e6f3d5a", "/Users/easemob-dn0164/Desktop/", "haha.jpg");
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatGroupShareFile() {
        ChatGroup result = EMClient.getInstance().chatGroups().deleteChatGroupShareFile("137490869583873", "eb9ae860-5acf-11eb-ad29-f3026e6f3d5a");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupMembers() {
        ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupMembers("137490869583873", 1, 10);
        System.out.println("result " + result);
    }

    @Test
    public void addChatGroupMember() {
        ChatGroup result = EMClient.getInstance().chatGroups().addChatGroupMember("137490869583873", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void batchAddChatGroupMember() {
        Set<String> members = new HashSet<>();
        members.add("testuser00014");
        members.add("testuser00015");

        ChatGroup result = EMClient.getInstance().chatGroups().batchAddChatGroupMember("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatGroupMember() {
        ChatGroup result = EMClient.getInstance().chatGroups().deleteChatGroupMember("137490869583873", "testuser00014");
        System.out.println("result " + result);
    }

    @Test
    public void batchDeleteChatGroupMember() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0002");

        ChatGroup result = EMClient.getInstance().chatGroups().batchDeleteChatGroupMember("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupAdminList() {
        ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupAdminList("137490869583873");
        System.out.println("result " + result);
    }

    @Test
    public void addChatGroupAdmin() {
        ChatGroup result = EMClient.getInstance().chatGroups().addChatGroupAdmin("137490869583873", "testuser00015");
        System.out.println("result " + result);
    }

    @Test
    public void removeChatGroupAdmin() {
        ChatGroup result = EMClient.getInstance().chatGroups().removeChatGroupAdmin("137490869583873", "testuser00015");
        System.out.println("result " + result);
    }

    @Test
    public void transferChatGroupAdmin() {
        ChatGroup result = EMClient.getInstance().chatGroups().transferChatGroupAdmin("137490869583873", "testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupBlocks() {
        ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupBlocks("137490869583873");
        System.out.println("result " + result);
    }

    @Test
    public void addUserToChatGroupBlocks() {
        ChatGroup result = EMClient.getInstance().chatGroups().addUserToChatGroupBlocks("137490869583873", "testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void batchAddUserToChatGroupBlocks() {
        Set<String> members = new HashSet<>();
        members.add("testuser0001");
//        members.add("testuser0005");

        EMProperties properties = new EMProperties("62242102#fudonghai89", "YXA66v11wCkrEeWC1yHU2wRelQ", "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0", "http://a1.easemob.com");

        ChatGroup result = EMClient.getInstance().chatGroups().batchAddUserToChatGroupBlocks("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void removeUserToChatGroupBlocks() {
        ChatGroup result = EMClient.getInstance().chatGroups().removeUserToChatGroupBlocks("137490869583873", "testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void batchRemoveUserToChatGroupBlocks() {
        Set<String> members = new HashSet<>();
        members.add("testuser0001");
//        members.add("testuser0005");

        ChatGroup result = EMClient.getInstance().chatGroups().batchRemoveUserToChatGroupBlocks("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void addMute() {
        ChatGroup result = EMClient.getInstance().chatGroups().addMute("137490869583873", "testuser0005", 100L);
        System.out.println("result " + result);
    }

    @Test
    public void testAddMute() {
        Set<String> members = new HashSet<>();
        members.add("testuser0002");
        members.add("testuser0005");

        ChatGroup result = EMClient.getInstance().chatGroups().addMute("137490869583873", members, 100L);
        System.out.println("result " + result);
    }

    @Test
    public void removeMute() {
        ChatGroup result = EMClient.getInstance().chatGroups().removeMute("137490869583873", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void testRemoveMute() {
        Set<String> members = new HashSet<>();
        members.add("testuser0002");
        members.add("testuser0005");

        ChatGroup result = EMClient.getInstance().chatGroups().removeMute("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void getMuteList() {
        ChatGroup result = EMClient.getInstance().chatGroups().getMuteList("137490869583873");
        System.out.println("result " + result);
    }
}