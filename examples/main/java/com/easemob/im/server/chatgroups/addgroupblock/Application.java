package com.easemob.im.server.chatgroups.addgroupblock;

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
            // 添加单个用户至群组黑名单示例
            ChatGroup result = EMClient.getInstance().chatGroups().addUserToChatGroupBlocks("137490869583873", "testuser0001");

            // 添加多个用户至群组黑名单示例
//            Set<String> members = new HashSet<>();
//            members.add("testuser0001");
//            members.add("testuser0005");
//            ChatGroup result = EMClient.getInstance().chatGroups().batchAddUserToChatGroupBlocks("137490869583873", members);

            System.out.println("result = " + result);
        } catch (EMClientException | ChatGroupsException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
