package com.easemob.im.server.chatgroups.addmute;

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
            // 将单个用户禁言示例
            ChatGroup result = EMClient.getInstance().chatGroups().addMute("137490869583873", "testuser0005", 10000L);

            // 将多个用户禁言示例
//            Set<String> members = new HashSet<>();
//            members.add("testuser0002");
//            members.add("testuser0005");
//            ChatGroup result = EMClient.getInstance().chatGroups().addMute("137490869583873", members, 10000L);

            System.out.println("result = " + result);
        } catch (EMClientException | ChatGroupsException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
