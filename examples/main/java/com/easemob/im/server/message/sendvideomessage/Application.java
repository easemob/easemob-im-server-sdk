package com.easemob.im.server.message.sendvideomessage;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.message.TargetType;
import com.easemob.im.server.api.message.exception.MessageException;
import com.easemob.im.server.model.Message;

import java.util.HashSet;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> usernames = new HashSet<>();
        usernames.add("testuser0002");

        String url = "http://a1.easemob.com/easemob-demo/chatdemoui/chatfiles/45046030-53db-11eb-a698-1326ab954f0d";
        String secret = "RQT8cFPbEeupq-8VehbJEh3vIl5Yy6Zftf4KFQ94hS9Saa-j";
        String thumbUrl = "http://a1.easemob.com/easemob-demo/chatdemoui/chatfiles/40c68e40-53c1-11eb-b335-f95073f12357";
        String thumbSecret = "QMa1WlPBEeul4RsbdlajcH9D6mMV530iTUldzwtzkwhKfolx";

        try {
            Message message = EMClient.getInstance().message().sendVideoMessage(TargetType.users, usernames, url, "video1", secret, 10, 56789L, thumbUrl, thumbSecret, "testuser0001", null);
            System.out.println("message = " + message);
        } catch (EMClientException | MessageException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
