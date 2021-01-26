package com.easemob.im.server.auth.gettoken;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.auth.exception.TokenException;

public class Application {

    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
            "YXA66v11wCkrEeWC1yHU2wRelQ",
            "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
            "http://a1.easemob.com"));

        try {
            String token = EMClient.getInstance().token().getToken();
            System.out.println("token = " + token);
        } catch (EMClientException | TokenException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }

}
