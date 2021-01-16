package com.easemob.im.server.api.chatgroups;

import com.easemob.im.server.EMClient;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ChatGroupsApiTest {

    @Test
    public void getAppAllChatGroup() {
        JsonNode result = EMClient.getInstance().chatGroups().getAppAllChatGroup(10, null);
        System.out.println("result " + result);
    }

    @Test
    public void getUserJoinAllChatGroup() {
        JsonNode result = EMClient.getInstance().chatGroups().getUserJoinAllChatGroup("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupDetails() {
        JsonNode result = EMClient.getInstance().chatGroups().getChatGroupDetails("137490869583873");
        System.out.println("result " + result);
    }

    @Test
    public void testGetChatGroupDetails() {
        // 137490750046209   137490869583873
        Set<String> members = new HashSet<>();
        members.add("137490750046209");
        members.add("137490869583873");

        JsonNode result = EMClient.getInstance().chatGroups().getChatGroupDetails(members);
        System.out.println("result " + result);
    }

    @Test
    public void createChatGroup() {
        Set<String> members = new HashSet<>();
        members.add("testuser0002");

        JsonNode result = EMClient.getInstance().chatGroups().createChatGroup("testChatGroup1", "test", true, 3, false, false, "testuser0001", members);
        System.out.println("result " + result);
    }

    @Test
    public void modifyChatGroupInfo() {
        JsonNode result = EMClient.getInstance().chatGroups().modifyChatGroupInfo("137490869583873", "modifyGroupName", "modifyDescription", 5, null, null);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatGroup() {
        JsonNode result = EMClient.getInstance().chatGroups().deleteChatGroup("137490750046209");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupAnnouncement() {
        JsonNode result = EMClient.getInstance().chatGroups().getChatGroupAnnouncement("137490869583873");
        System.out.println("result " + result);
    }

    @Test
    public void modifyChatGroupAnnouncement() {
        JsonNode result = EMClient.getInstance().chatGroups().modifyChatGroupAnnouncement("137490869583873", "群组公告");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupShareFile() {
        JsonNode result = EMClient.getInstance().chatGroups().getChatGroupShareFile("137689089245185");
        System.out.println("result " + result);
    }

    @Test
    public void testGetChatGroupShareFile() {
        JsonNode result = EMClient.getInstance().chatGroups().getChatGroupShareFile("137689089245185", 1, 5);
        System.out.println("result " + result);
    }

    @Test
    public void uploadChatGroupShareFile() {
        File file = new File("/Users/easemob-dn0164/Desktop/9090.jpg");
        JsonNode result = EMClient.getInstance().chatGroups().uploadChatGroupShareFile("137689089245185", file);
        System.out.println("result " + result);
    }

    @Test
    public void downloadChatGroupShareFile() {
        JsonNode result = EMClient.getInstance().chatGroups().downloadChatGroupShareFile("137689089245185", "f89d3910-5722-11eb-9bc8-bd7212a40a97", "/Users/easemob-dn0164/Desktop/", "haha.jpg");
        System.out.println("result " + result);
    }


    @Test
    public void deleteChatGroupShareFile() {
        JsonNode result = EMClient.getInstance().chatGroups().deleteChatGroupShareFile("137689089245185", "f89d3910-5722-11eb-9bc8-bd7212a40a97");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupMembers() {
        JsonNode result = EMClient.getInstance().chatGroups().getChatGroupMembers("137490869583873", 1, 10);
        System.out.println("result " + result);
    }

    @Test
    public void addChatGroupMember() {
        JsonNode result = EMClient.getInstance().chatGroups().addChatGroupMember("137490869583873", "testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void batchAddChatGroupMember() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0006");

        JsonNode result = EMClient.getInstance().chatGroups().batchAddChatGroupMember("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void deleteChatGroupMember() {
        JsonNode result = EMClient.getInstance().chatGroups().deleteChatGroupMember("137490869583873", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void batchDeleteChatGroupMember() {
        Set<String> members = new HashSet<>();
        members.add("testuser0003");
        members.add("testuser0002");

        JsonNode result = EMClient.getInstance().chatGroups().batchDeleteChatGroupMember("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupAdminList() {
        JsonNode result = EMClient.getInstance().chatGroups().getChatGroupAdminList("137490869583873");
        System.out.println("result " + result);
    }

    @Test
    public void addChatGroupAdmin() {
        JsonNode result = EMClient.getInstance().chatGroups().addChatGroupAdmin("137490869583873", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void removeChatGroupAdmin() {
        JsonNode result = EMClient.getInstance().chatGroups().removeChatGroupAdmin("137490869583873", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void transferChatGroupAdmin() {
        JsonNode result = EMClient.getInstance().chatGroups().transferChatGroupAdmin("137490869583873", "testuser0006");
        System.out.println("result " + result);
    }

    @Test
    public void getChatGroupBlocks() {
        JsonNode result = EMClient.getInstance().chatGroups().getChatGroupBlocks("137490869583873");
        System.out.println("result " + result);
    }

    @Test
    public void addUserToChatGroupBlocks() {
        JsonNode result = EMClient.getInstance().chatGroups().addUserToChatGroupBlocks("137490869583873", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void batchAddUserToChatGroupBlocks() {
        Set<String> members = new HashSet<>();
        members.add("testuser0002");
        members.add("testuser0005");

        JsonNode result = EMClient.getInstance().chatGroups().batchAddUserToChatGroupBlocks("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void removeUserToChatGroupBlocks() {
        JsonNode result = EMClient.getInstance().chatGroups().removeUserToChatGroupBlocks("137490869583873", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void batchRemoveUserToChatGroupBlocks() {
        Set<String> members = new HashSet<>();
        members.add("testuser0002");
        members.add("testuser0005");

        JsonNode result = EMClient.getInstance().chatGroups().batchRemoveUserToChatGroupBlocks("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void addMute() {
        JsonNode result = EMClient.getInstance().chatGroups().addMute("137490869583873", "testuser0005", 100L);
        System.out.println("result " + result);
    }

    @Test
    public void testAddMute() {
        Set<String> members = new HashSet<>();
        members.add("testuser0002");
        members.add("testuser0005");

        JsonNode result = EMClient.getInstance().chatGroups().addMute("137490869583873", members, 100L);
        System.out.println("result " + result);
    }

    @Test
    public void removeMute() {
        JsonNode result = EMClient.getInstance().chatGroups().removeMute("137490869583873", "testuser0005");
        System.out.println("result " + result);
    }

    @Test
    public void testRemoveMute() {
        Set<String> members = new HashSet<>();
        members.add("testuser0002");
        members.add("testuser0005");

        JsonNode result = EMClient.getInstance().chatGroups().removeMute("137490869583873", members);
        System.out.println("result " + result);
    }

    @Test
    public void getMuteList() {
        JsonNode result = EMClient.getInstance().chatGroups().getMuteList("137490869583873");
        System.out.println("result " + result);
    }
}