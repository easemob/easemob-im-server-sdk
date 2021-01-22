package com.easemob.im.server.user.getofflinemessagestatus;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.user.exception.UserException;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            String result = EMClient.getInstance().user().getOfflineMessageStatus("testuser0001", "123132342342");
            System.out.println("result = " + result);
        } catch (EMClientException | UserException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
