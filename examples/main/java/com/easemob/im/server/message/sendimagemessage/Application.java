package com.easemob.im.server.message.sendimagemessage;

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

        Map<String, Object> ext = new HashMap<>();
        ext.put("key", "value");
        String url = "http://a1.easemob.com/easemob-demo/chatdemoui/chatfiles/40c68e40-53c1-11eb-b335-f95073f12357";
        String secret = "QMa1WlPBEeul4RsbdlajcH9D6mMV530iTUldzwtzkwhKfolx";

        try {
            Message message = EMClient.getInstance().message().sendImageMessage(TargetType.users, usernames, url, "image1", secret, 200L, null, "testuser0001", ext);
            System.out.println("message = " + message);
        } catch (EMClientException | MessageException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
