package com.easemob.im.server.api.metadata;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.exception.EMForbiddenException;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.model.EMUser;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

// TODO: need tests UT/IT
public class TokenApi {

    private static final String ERROR_MSG = "failed to build AccessToken2";
    private static final Logger log = LoggerFactory.getLogger(TokenApi.class);

    private final Context context;

    public TokenApi(Context context) {
        this.context = context;
    }

    public String generateUserToken(EMUser user, int expireInSeconds, Consumer<AccessToken2> tokenConfigurer) {
        String userId = user.getUuid();
        EMProperties properties = context.getProperties();
        String appId = properties.getAppId();
        String appCert = properties.getAppCert();
        int expireOnSeconds = toExpireOnSeconds(expireInSeconds);

        AccessToken2 accessToken = new AccessToken2(appId, appCert, expireOnSeconds);
        AccessToken2.Service serviceChat = new AccessToken2.ServiceChat(userId);
        serviceChat.addPrivilegeChat(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER, expireOnSeconds);
        accessToken.addService(serviceChat);

        tokenConfigurer.accept(accessToken);
        validateUserChatToken(accessToken);

        try {
            return accessToken.build();
        } catch (Exception e) {
            log.error(ERROR_MSG, e);
            throw new EMInvalidStateException(ERROR_MSG);
        }
    }

    private static int toExpireOnSeconds(int expireInSeconds) {
        return (int) (Instant.now().plusSeconds(expireInSeconds).toEpochMilli() / 1000);
    }

    // must include userId if it has the chat.user privilege
    // must not have chat.app privilege
    private void validateUserChatToken(AccessToken2 token) {
        AccessToken2.Service service = token.services.get(AccessToken2.SERVICE_TYPE_CHAT);
        if (service == null) {
            return;
        }
        AccessToken2.ServiceChat serviceChat = (AccessToken2.ServiceChat) service;
        String userId = serviceChat.getUserId();
        Map<Short, Integer> chatPrivileges = serviceChat.getPrivileges();
        boolean hasUserId = Strings.isNotBlank(userId);
        boolean hasAppPrivilege = chatPrivileges.get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP.intValue) != null;
        boolean hasUserPrivilege = chatPrivileges.get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER.intValue) != null;
        if (hasAppPrivilege) {
            throw new EMForbiddenException("userToken must not have chat app privilege");
        }
        if (hasUserPrivilege && !hasUserId) {
            throw new EMForbiddenException("accessToken with the chatUser privilege must include an userId");
        }
    }

}
