package com.easemob.im.server.api.user;

import com.easemob.im.server.EMClient;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.user.exception.UserException;
import com.easemob.im.server.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.handler.codec.http.HttpMethod;
import javafx.beans.binding.When;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.nio.charset.Charset;
import java.util.*;

import static org.junit.Assert.*;

public class UserApiTest {

    @Mock
    private EMProperties properties;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private HttpClient client;

    @Mock
    private ByteBufAllocator allocator;

    private UserApi userApi;

    @Before
    public void initMocks() {
        userApi = new UserApi(client, mapper, allocator);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerUserException(){

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
        UserApi mock = Mockito.mock(UserApi.class);
//        when(mock.register("用户名", null, null).thenThrow(new UserException("Bad Request 用户名 invalid username"));

    }

    @Test
    public void registerUserResponse401(){
    }


    @Test
    public void batchRegisterUser(){

        BatchRegisterUser user1 = new BatchRegisterUser("testuser0001", "1", "昵称1");
//        BatchRegisterUser user2 = new BatchRegisterUser("testuser0009", "1", "昵称2");
//        BatchRegisterUser user3 = new BatchRegisterUser("testuser00010", "1", "昵称3");

        Set<BatchRegisterUser> setUsers = new HashSet<>();
        setUsers.add(user1);
//        setUsers.add(user2);
//        setUsers.add(user3);

        User result = EMClient.getInstance().user().batchRegister(setUsers);
        System.out.println("result " + result);
    }

    @Test
    public void getUser(){
        User result = EMClient.getInstance().user().getUser("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void batchGetUser(){
        User result = EMClient.getInstance().user().batchGetUser(1, null);
        System.out.println("result " + result);
    }

    @Test
    public void deleteUser(){
        User result = EMClient.getInstance().user().deleteUser("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void batchDeleteUser(){
        User result = EMClient.getInstance().user().batchDeleteUser(2, null);
        System.out.println("result " + result);
    }

    @Test
    public void modifyUserPassword(){
        Map<String, Object> result = EMClient.getInstance().user().modifyUserPassword("testuser0001", "123456");
        System.out.println("result " + result);
    }

    @Test
    public void setUserPushNickname(){
        User result = EMClient.getInstance().user().setUserPushNickname("testuser0001", "pushNickname");
        System.out.println("result " + result);
    }

    @Test
    public void setNotificationDisplayStyle(){
        User result = EMClient.getInstance().user().setNotificationDisplayStyle("testuser0001", 0);
        System.out.println("result " + result);
    }

    @Test
    public void setNotificationNoDisturbing(){
        User result = EMClient.getInstance().user().setNotificationNoDisturbing("testuser0001", 5, 12);
        System.out.println("result " + result);
    }

    @Test
    public void cancelNotificationNoDisturbing(){
        User result = EMClient.getInstance().user().cancelNotificationNoDisturbing("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void addContact(){
        User result = EMClient.getInstance().user().addContact("testuser0001", "testuser0002");
        System.out.println("result " + result);
    }

    @Test
    public void removeContact(){
        User result = EMClient.getInstance().user().removeContact("testuser0001", "testuser0002");
        System.out.println("result " + result);
    }

    @Test
    public void getContactList(){
        List<String> result = EMClient.getInstance().user().getContactList("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void getBlockList(){
        List<String> result = EMClient.getInstance().user().getBlockList("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void addBlock(){
        Set<String> usernames = new HashSet<>();
        usernames.add("testuser0005");

        List<String> result = EMClient.getInstance().user().addBlock("testuser0001", usernames);
        System.out.println("result " + result);
    }

    @Test
    public void removeBlock(){
        User result = EMClient.getInstance().user().removeBlock("testuser0001", "testuser0002");
        System.out.println("result " + result);
    }


    @Test
    public void getUserStatus(){
        String result = EMClient.getInstance().user().getUserStatus("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void batchGetUserStatus(){
        Set<String> usernames = new HashSet<>();
        usernames.add("testuser0001");
        usernames.add("testuser0002");

        List<Map<String, String>> result = EMClient.getInstance().user().batchGetUserStatus(usernames);
        System.out.println("result " + result);
    }

    @Test
    public void getUserOfflineMessageCount(){
        int result = EMClient.getInstance().user().getUserOfflineMessageCount("testuser0001");
        System.out.println("result " + result);
    }

    @Test
    public void getOfflineMessageStatus(){
        String result = EMClient.getInstance().user().getOfflineMessageStatus("testuser0001", "123132342342");
        System.out.println("result " + result);
    }

    @Test
    public void deactivateUser(){
        User result = EMClient.getInstance().user().deactivateUser("testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void activateUser(){
        Map<String, Object> result = EMClient.getInstance().user().activateUser("testuser0003");
        System.out.println("result " + result);
    }

    @Test
    public void disconnect(){
        boolean result = EMClient.getInstance().user().disconnect("testuser0001");
        System.out.println("result " + result);
    }

}