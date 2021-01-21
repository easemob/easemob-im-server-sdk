package com.easemob.im.server.api.chatfiles;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.model.ChatFile;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.io.File;

public class ChatFilesApiTest {

    @Test
    public void uploadAttachment() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        File file = new File("/Users/easemob-dn0164/Desktop/9090.jpg");
        ChatFile result = EMClient.getInstance().chatFiles().uploadAttachment(file);
//        JsonNode result = EMClient.getInstance().chatFiles().uploadAttachment("/Users/easemob-dn0164/Desktop/9090.jpg");
        System.out.println("result " + result);
    }

    // 7be1aa50-5619-11eb-be56-c11f9dd9c15e
    // e-HRYFYZEeurb-Uza3Hc_1sV4yAiLHnff-wPmGuxw1M0c-X2

    @Test
    public void downloadAttachment() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        JsonNode result = EMClient.getInstance().chatFiles().downloadAttachment("v_1A-lZQEeuRuQ0i1sAAnjLoDYbPanUGt3U-8iTIpUtP-xXd", "bffd40f0-5650-11eb-8a1a-bdd36d4848b3", "/Users/easemob-dn0164/Desktop", "888.txt");
        System.out.println("result " + result);
    }

    @Test
    public void downloadThumbnail() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        JsonNode result = EMClient.getInstance().chatFiles().downloadThumbnail("e-HRYFYZEeurb-Uza3Hc_1sV4yAiLHnff-wPmGuxw1M0c-X2", "7be1aa50-5619-11eb-be56-c11f9dd9c15e", "/Users/easemob-dn0164/Desktop", "99.jpg");
        System.out.println("result " + result);
    }



}