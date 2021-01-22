package com.easemob.im.server.user.getuserstatus;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.user.exception.UserException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            // 获取单个用户在线状态示例
            String result = EMClient.getInstance().user().getUserStatus("testuser0001");

//            // 获取多个用户在线状态示例
//            Set<String> usernames = new HashSet<>();
//            usernames.add("testuser0001");
//            usernames.add("testuser0002");
//            List<Map<String, String>> result = EMClient.getInstance().user().batchGetUserStatus(usernames);

            System.out.println("result = " + result);
        } catch (EMClientException | UserException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
