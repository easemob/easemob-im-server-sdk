package com.easemob.im.server.api.chatmessages;

import com.easemob.im.server.EMClient;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import static org.junit.Assert.*;

public class ChatMessagesApiTest {

    @Test
    public void getHistoryMessage() {
        String url = EMClient.getInstance().chatMessages().getHistoryMessage(2021011321L);
        System.out.println("url " + url);
    }

    @Test
    public void getHistoryMessageAndAutoDownloadFile() {
        JsonNode result = EMClient.getInstance().chatMessages().getHistoryMessageAndAutoDownloadFile(2021011321L, "/Users/easemob-dn0164/Desktop");
        System.out.println("url " + result);
    }



}