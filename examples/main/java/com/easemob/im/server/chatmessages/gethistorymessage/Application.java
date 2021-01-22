package com.easemob.im.server.chatmessages.gethistorymessage;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatmessages.exception.ChatMessagesException;
import com.fasterxml.jackson.databind.JsonNode;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            // 根据时间从环信服务器获取历史消息文件存储的url
            String url = EMClient.getInstance().chatMessages().getHistoryMessage(2021011321L);
            System.out.println("url = " + url);

            // 根据时间从环信服务器获取历史消息文件存储的url并根据指定的本地路径自动下载文件
            JsonNode result = EMClient.getInstance().chatMessages().getHistoryMessageAndAutoDownloadFile(2021011321L, "/Users/easemob-dn0164/Desktop");
            System.out.println("result = " + result);

        } catch (EMClientException | ChatMessagesException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
