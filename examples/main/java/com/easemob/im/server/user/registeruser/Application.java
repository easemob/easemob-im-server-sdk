package com.easemob.im.server.user.registeruser;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.user.BatchRegisterUser;
import com.easemob.im.server.api.user.exception.UserException;
import com.easemob.im.server.model.User;

import java.util.HashSet;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        try {
            // 注册单个用户示例
            User result = EMClient.getInstance().user().register("testuser00001", "1", null);

            // 注册多个用户示例
//            BatchRegisterUser user1 = new BatchRegisterUser("testuser00001", "1", "昵称1");
//            BatchRegisterUser user2 = new BatchRegisterUser("testuser00002", "1", "昵称2");
//            BatchRegisterUser user3 = new BatchRegisterUser("testuser00003", "1", "昵称3");
//
//            Set<BatchRegisterUser> setUsers = new HashSet<>();
//            setUsers.add(user1);
//            setUsers.add(user2);
//            setUsers.add(user3);
//            User result = EMClient.getInstance().user().batchRegister(setUsers);

            System.out.println("result = = " + result);
        } catch (UserException e) {
            System.out.println("exception = " + e.getMessage());
        }
    }
}
