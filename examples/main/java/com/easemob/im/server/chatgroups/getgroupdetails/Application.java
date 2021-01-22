package com.easemob.im.server.chatgroups.getgroupdetails;

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
            // 获取单个群组详情示例
            ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupDetails("137490869583873");
            System.out.println("result = " + result);

            // 获取多个群组详情示例
//            Set<String> groupIds = new HashSet<>();
//            groupIds.add("138112755892225");
//            groupIds.add("137490869583873");
//            ChatGroup result = EMClient.getInstance().chatGroups().getChatGroupDetails(groupIds);

        } catch (EMClientException | ChatGroupsException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
