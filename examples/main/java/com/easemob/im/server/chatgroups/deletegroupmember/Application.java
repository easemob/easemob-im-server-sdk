package com.easemob.im.server.chatgroups.deletegroupmember;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatgroups.exception.ChatGroupsException;
import com.easemob.im.server.model.ChatGroup;

import java.util.HashSet;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            // 删除单个群组成员示例
            ChatGroup result = EMClient.getInstance().chatGroups().deleteChatGroupMember("137490869583873", "testuser00014");

            // 删除多个群组成员示例
//            Set<String> members = new HashSet<>();
//            members.add("testuser0003");
//            members.add("testuser0002");
//            ChatGroup result = EMClient.getInstance().chatGroups().batchDeleteChatGroupMember("137490869583873", members);

            System.out.println("result = " + result);
        } catch (EMClientException | ChatGroupsException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
