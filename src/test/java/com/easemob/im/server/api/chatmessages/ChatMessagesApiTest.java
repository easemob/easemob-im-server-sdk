package com.easemob.im.server.api.chatmessages;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatmessages.exception.ChatMessagesException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

public class ChatMessagesApiTest {

    @Test
    public void getHistoryMessage() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            String url = EMClient.getInstance().chatMessages().getHistoryMessage(2021011321L);
            System.out.println("url = " + url);
        } catch (EMClientException | ChatMessagesException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }

    @Test
    public void getHistoryMessageAndAutoDownloadFile() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            JsonNode result = EMClient.getInstance().chatMessages().getHistoryMessageAndAutoDownloadFile(2021011321L, "/Users/easemob-dn0164/Desktop");
            System.out.println("url = " + result);
        } catch (EMClientException | ChatMessagesException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }

}