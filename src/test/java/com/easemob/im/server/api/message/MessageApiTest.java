package com.easemob.im.server.api.message;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.message.exception.MessageException;
import com.easemob.im.server.model.Message;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MessageApiTest {

    @Test
    public void sendTextMessage() {
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

    @Test
    public void sendImageMessage() {
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

    @Test
    public void sendAudioMessage() {
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

    @Test
    public void sendVideoMessage() {
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

    @Test
    public void sendLocationMessage() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> usernames = new HashSet<>();
        usernames.add("testuser0002");

        try {
            Message message = EMClient.getInstance().message().sendLocationMessage(TargetType.users, usernames, "116.322", "39.966", "中国北京市海淀区中关村", "testuser0001", null);
            System.out.println("message = " + message);
        } catch (EMClientException | MessageException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }

    @Test
    public void sendCmdMessage() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> usernames = new HashSet<>();
        usernames.add("testuser0002");

        try {
            Message message = EMClient.getInstance().message().sendCmdMessage(TargetType.users, usernames, "action", "testuser0001", null);
            System.out.println("message = " + message);
        } catch (EMClientException | MessageException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }

    @Test
    public void sendCustomMessage() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> usernames = new HashSet<>();
        usernames.add("testuser0002");

        Map<String, Object> customExtMap = new HashMap<>();
        customExtMap.put("key", "value");

        try {
            Message message = EMClient.getInstance().message().sendCustomMessage(TargetType.users, usernames, "gif", customExtMap, "testuser0001", null);
            System.out.println("message = " + message);
        } catch (EMClientException | MessageException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}