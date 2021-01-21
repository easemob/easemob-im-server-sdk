package com.easemob.im.server.api.chatfiles;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatfiles.exception.ChatFilesException;
import com.easemob.im.server.model.ChatFile;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.io.File;

public class ChatFilesApiTest {

    @Test
    public void testUploadAttachment() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        try {
            ChatFile result = EMClient.getInstance().chatFiles().uploadAttachment("/Users/easemob-dn0164/Desktop/9090111.jpg");
            System.out.println("result = = " + result);
        } catch (EMClientException | ChatFilesException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }

    @Test
    public void uploadAttachment() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        File file = new File("/Users/easemob-dn0164/Desktop/9090.jpg");
        try {
            ChatFile result = EMClient.getInstance().chatFiles().uploadAttachment(file);
            System.out.println("result = " + result);
        } catch (EMClientException | ChatFilesException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }

    // 7be1aa50-5619-11eb-be56-c11f9dd9c15e
    // e-HRYFYZEeurb-Uza3Hc_1sV4yAiLHnff-wPmGuxw1M0c-X2

    @Test
    public void downloadAttachment() {
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

    @Test
    public void downloadThumbnail() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            JsonNode result = EMClient.getInstance().chatFiles().downloadThumbnail("eimMQFu6EeuEEcsJM8eBtTDqVBKphyYdf9rL9YN42zxnvPpV", "7a296530-5bba-11eb-9543-ab28d323b646", "/Users/easemob-dn0164/Desktop", "99.jpg");
            System.out.println("result = " + result);
        } catch (EMClientException | ChatFilesException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }

}