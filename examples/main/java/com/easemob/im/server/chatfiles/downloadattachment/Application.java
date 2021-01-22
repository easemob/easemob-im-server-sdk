package com.easemob.im.server.chatfiles.downloadattachment;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatfiles.exception.ChatFilesException;
import com.fasterxml.jackson.databind.JsonNode;


public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            JsonNode result = EMClient.getInstance().chatFiles().downloadAttachment("eimMQFu6EeuEEcsJM8eBtTDqVBKphyYdf9rL9YN42zxnvPpV", "7a296530-5bba-11eb-9543-ab28d323b646", "/Users/easemob-dn0164/Desktop", "888.jpg");
            System.out.println("result = " + result);
        } catch (EMClientException | ChatFilesException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
