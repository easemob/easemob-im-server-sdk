package com.easemob.im.server.api.token;

import com.easemob.im.server.EMClient;
import org.junit.Test;

import static org.junit.Assert.*;

public class TokenApiTest {
    @Test
    public void getToken(){
        String token = EMClient.getInstance().token().getToken();
        System.out.println("token = " + token);
    }
}