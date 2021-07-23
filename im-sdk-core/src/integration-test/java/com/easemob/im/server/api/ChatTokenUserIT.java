package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.api.token.agora.AccessToken2.PrivilegeChat;
import com.easemob.im.server.exception.EMNotFoundException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static com.easemob.im.server.utils.RandomMaker.makeRandomUserName;

public class ChatTokenUserIT {

    private static final String BASE_URI = System.getenv("IM_BASE_URI");
    private static final String APP_KEY = System.getenv("IM_APPKEY");
    private static final String APP_ID = System.getenv("IM_APP_ID");
    private static final String APP_CERT = System.getenv("IM_APP_CERT");

    private static final int EXPIRE_SECONDS = 3601;
    private static final String PASSWORD = "password";
    private static final String USER_ID = "da9287a0-ecf9-11eb-9af3-296ff79acb67";

    private static final Logger log = LoggerFactory.getLogger(ChatTokenUserIT.class);

    private EMService service;

    ChatTokenUserIT() {
        EMProperties properties = EMProperties.agoraRealmBuilder()
                .setBaseUri(BASE_URI)
                .setAppkey(APP_KEY)
                .setAppId(APP_ID)
                .setAppCert(APP_CERT)
                .setHttpConnectionPoolSize(10)
                .setServerTimezone("+8")
                .build();

        this.service = new EMService(properties);
    }

    @Test
    public void userCrud() {
        String randomUsername = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, PASSWORD)
                .block());
        assertDoesNotThrow(
                () -> this.service.user().get(randomUsername).block());
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block());
        assertThrows(EMNotFoundException.class,
                () -> this.service.user().get(randomUsername).block());
    }

    @Test
    public void buildChatUserToken() throws Exception {
        Token userToken = service.user().getToken(USER_ID,
                EXPIRE_SECONDS,token -> {}).block();
        assertNotNull(userToken);
        String userTokenValue = userToken.getValue();
        AccessToken2 token2 = new AccessToken2();
        token2.parse(userTokenValue);
        Map<Short, AccessToken2.Service> services = token2.services;
        assertEquals(1, services.size());
        AccessToken2.Service service = services.get(AccessToken2.SERVICE_TYPE_CHAT);
        AccessToken2.ServiceChat serviceChat = (AccessToken2.ServiceChat) service;
        String userIdInToken = serviceChat.getUserId();
        assertEquals(USER_ID, userIdInToken);
        Map<Short, Integer> privileges = service.getPrivileges();
        assertEquals(1, privileges.size());
        int expireInToken = privileges.get(PrivilegeChat.PRIVILEGE_CHAT_USER.intValue);
        assertEquals(EXPIRE_SECONDS, expireInToken);
    }
}

