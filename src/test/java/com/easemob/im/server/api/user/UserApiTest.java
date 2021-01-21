package com.easemob.im.server.api.user;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.model.User;

import org.junit.Test;
import java.util.*;


public class UserApiTest {

    @Test
    public void registerUser() {

//        ObjectNode request = this.mapper.createObjectNode();
//        request.put("username", "testuser0005");
//        request.put("password", "1");
//        ByteBuf bb = allocator.buffer();
//        bb.writeCharSequence(request.toString(), Charset.forName("UTF-8"));
//
//        client.request(HttpMethod.POST)
//                .uri("http://a1.easemob.com/easemob-demo/chatdemoui/users")
//                .send(Mono.just(bb))
//                .response(()) // 模拟线上返回的结果 {duration:80}

//        JsonNode jsonNode = new JsonNode(); //预期的结果
//        UserApi mock = Mockito.mock(UserApi.class);
//        when(mock.register("用户名", null, null).thenThrow(new UserException("Bad Request 用户名 invalid username"));
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().register("testuser0001", "1", null);
        System.out.println("result " + result);
    }

    @Test
    public void batchRegisterUser() {
        BatchRegisterUser user1 = new BatchRegisterUser("testuser0001", "1", "昵称1");
//        BatchRegisterUser user2 = new BatchRegisterUser("testuser0009", "1", "昵称2");
//        BatchRegisterUser user3 = new BatchRegisterUser("testuser00010", "1", "昵称3");

        Set<BatchRegisterUser> setUsers = new HashSet<>();
        setUsers.add(user1);
//        setUsers.add(user2);
//        setUsers.add(user3);
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().batchRegister(setUsers);
        System.out.println("result " + result);
    }

    @Test
    public void getUser() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));

        User result = EMClient.getInstance().user().getUser("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void batchGetUser() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().batchGetUser(1, null);
        System.out.println("result " + result);
    }

    @Test
    public void deleteUser() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().deleteUser("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void batchDeleteUser() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().batchDeleteUser(2, null);
        System.out.println("result " + result);
    }

    @Test
    public void modifyUserPassword() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Map<String, Object> result = EMClient.getInstance().user().modifyUserPassword("testuser0001", "123456");
        System.out.println("result " + result);
    }

    @Test
    public void setUserPushNickname() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().setUserPushNickname("testuser0001", "pushNickname");
        System.out.println("result " + result);
    }

    @Test
    public void setNotificationDisplayStyle() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().setNotificationDisplayStyle("testuser0001", 0);
        System.out.println("result " + result);
    }

    @Test
    public void setNotificationNoDisturbing() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().setNotificationNoDisturbing("testuser0001", 5, 12);
        System.out.println("result " + result);
    }

    @Test
    public void cancelNotificationNoDisturbing() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().cancelNotificationNoDisturbing("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void addContact() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().addContact("testuser0001", "testuser0002");
        System.out.println("result " + result);
    }

    @Test
    public void removeContact() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().removeContact("testuser0001", "testuser0002");
        System.out.println("result " + result);
    }

    @Test
    public void getContactList() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        List<String> result = EMClient.getInstance().user().getContactList("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void getBlockList() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        List<String> result = EMClient.getInstance().user().getBlockList("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void addBlock() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> usernames = new HashSet<>();
        usernames.add("testuser0005");

        List<String> result = EMClient.getInstance().user().addBlock("testuser0001", usernames);
        System.out.println("result " + result);
    }

    @Test
    public void removeBlock() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().removeBlock("testuser0001", "testuser0002");
        System.out.println("result " + result);
    }


    @Test
    public void getUserStatus() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        String result = EMClient.getInstance().user().getUserStatus("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void batchGetUserStatus() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Set<String> usernames = new HashSet<>();
        usernames.add("testuser0001");
        usernames.add("testuser0002");

        List<Map<String, String>> result = EMClient.getInstance().user().batchGetUserStatus(usernames);
        System.out.println("result " + result);
    }

    @Test
    public void getUserOfflineMessageCount() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        int result = EMClient.getInstance().user().getUserOfflineMessageCount("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void getOfflineMessageStatus() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        String result = EMClient.getInstance().user().getOfflineMessageStatus("testuser0001", "123132342342");
        System.out.println("result " + result);
    }

    @Test
    public void deactivateUser() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        User result = EMClient.getInstance().user().deactivateUser("testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void activateUser() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        Map<String, Object> result = EMClient.getInstance().user().activateUser("testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void disconnect() {
        EMClient.initializeProperties(new EMProperties("62242102#fudonghai89",
                "YXA66v11wCkrEeWC1yHU2wRelQ",
                "YXA6PhaHtRWPtfVQeiL-kEvVx4mktl0",
                "http://a1.easemob.com"));
        boolean result = EMClient.getInstance().user().disconnect("testuser0001");
        System.out.println("result " + result);
    }

}