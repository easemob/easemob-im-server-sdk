package com.easemob.im.server.chatgroups.downloadgroupsharefile;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatgroups.exception.ChatGroupsException;
import com.fasterxml.jackson.databind.JsonNode;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            JsonNode result = EMClient.getInstance().chatGroups().downloadChatGroupShareFile("137490869583873", "eb9ae860-5acf-11eb-ad29-f3026e6f3d5a", "/Users/easemob-dn0164/Desktop/", "haha.jpg");
            System.out.println("result = " + result);
        } catch (EMClientException | ChatGroupsException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
