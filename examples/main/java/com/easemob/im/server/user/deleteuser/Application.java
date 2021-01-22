package com.easemob.im.server.user.deleteuser;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMClientException;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.user.exception.UserException;
import com.easemob.im.server.model.User;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            // 删除单个用户示例
            User result = EMClient.getInstance().user().deleteUser("testuser0001");

            // 删除多个用户示例
//            User result = EMClient.getInstance().user().batchDeleteUser(2, null);

            System.out.println("result = " + result);
        } catch (EMClientException | UserException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
