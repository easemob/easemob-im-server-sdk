package com.easemob.im.server.message.sendaudiomessage;

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

        String url = "http://a1.easemob.com/easemob-demo/chatdemoui/chatfiles/3007b700-53da-11eb-8172-e3a2759f2f8f";
        String secret = "MAe3ClPaEeu-gDNNZV_yCEdJz8A370hCD_7MkYSeWRUDV97J";

        try {
            Message message = EMClient.getInstance().message().sendAudioMessage(TargetType.users, usernames, url, "audio1", secret, 3, "testuser0001", null);
            System.out.println("message = " + message);
        } catch (EMClientException | MessageException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
