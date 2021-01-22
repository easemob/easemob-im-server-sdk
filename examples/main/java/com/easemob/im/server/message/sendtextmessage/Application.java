package com.easemob.im.server.message.sendtextmessage;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.message.TargetType;
import com.easemob.im.server.api.message.exception.MessageException;
import com.easemob.im.server.model.Message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> usernames = new HashSet<>();
        usernames.add("testuser0002");
        usernames.add("testuser0003");

        Map<String, Object> extMap = new HashMap<>();
        extMap.put("key", "value");

        try {
            Message message = EMClient.getInstance().message().sendTextMessage(TargetType.users, usernames,"你好", "testuser0001", extMap);
            System.out.println("message = " + message);
        } catch (EMClientException | MessageException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
