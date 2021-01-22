package com.easemob.im.server.chatgroups.addgroupmember;

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
            // 添加单个群组成员示例
            ChatGroup result = EMClient.getInstance().chatGroups().addChatGroupMember("137490869583873", "testuser0005");

            // 添加多个群组成员示例
//            Set<String> members = new HashSet<>();
//            members.add("testuser00014");
//            members.add("testuser00015");
//            ChatGroup result = EMClient.getInstance().chatGroups().batchAddChatGroupMember("137490869583873", members);

            System.out.println("result = " + result);
        } catch (EMClientException | ChatGroupsException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
