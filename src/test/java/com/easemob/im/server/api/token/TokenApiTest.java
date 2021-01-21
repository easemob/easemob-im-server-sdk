package com.easemob.im.server.api.token;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMProperties;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenApiTest {
    @Test
    public void getToken(){
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        String token = EMClient.getInstance().token().getToken();
        System.out.println("token = " + token);
    }
}