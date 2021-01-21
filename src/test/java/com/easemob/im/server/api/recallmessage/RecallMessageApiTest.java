package com.easemob.im.server.api.recallmessage;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.user.BatchRegisterUser;
import com.easemob.im.server.model.RecallMessage;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RecallMessageApiTest {

    @Test
    public void recallMessage() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        JsonNode result = EMClient.getInstance().recall().recallMessage("831373505038649392", "testuser0001", ChatType.chat);
        System.out.println("result " + result);
    }

    @Test
    public void testRecallMessage() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        RecallMessage message = RecallMessage.builder()
                .messageId("831373815832385584")
                .to("testuser0001")
                .chatType(ChatType.chat)
                .build();

        Set<RecallMessage> messages = new HashSet<>();
        messages.add(message);

        JsonNode result = EMClient.getInstance().recall().recallMessage(messages);
        System.out.println("result " + result);
    }
}