package com.easemob.im.server.recallmessage.recall;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.recallmessage.ChatType;
import com.easemob.im.server.api.recallmessage.exception.RecallMessageException;
import com.easemob.im.server.model.RecallMessage;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashSet;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            // 撤回单条消息示例
            JsonNode result = EMClient.getInstance().recall().recallMessage("831373505038649392", "testuser0001", ChatType.chat);

            // 撤回多条消息示例
//            RecallMessage message = RecallMessage.builder()
//                    .messageId("831373815832385584")
//                    .to("testuser0001")
//                    .chatType(ChatType.chat)
//                    .build();
//
//            Set<RecallMessage> messages = new HashSet<>();
//            messages.add(message);
//            JsonNode result = EMClient.getInstance().recall().recallMessage(messages);

            System.out.println("result = " + result);
        } catch (EMClientException | RecallMessageException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
