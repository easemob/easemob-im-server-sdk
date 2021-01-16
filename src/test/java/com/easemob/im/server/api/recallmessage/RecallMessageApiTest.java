package com.easemob.im.server.api.recallmessage;

import com.easemob.im.server.EMClient;
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
        JsonNode result = EMClient.getInstance().recall().recallMessage("829573059890387008", "testuser0003", ChatType.chat);
        System.out.println("result " + result);
    }

    @Test
    public void testRecallMessage() {

        RecallMessage message = RecallMessage.builder()
                .messageId("829572799466047628")
                .to("testuser0002")
                .chatType(ChatType.chat)
                .build();

        Set<RecallMessage> messages = new HashSet<>();
        messages.add(message);

        JsonNode result = EMClient.getInstance().recall().recallMessage(messages);
        System.out.println("result " + result);
    }
}