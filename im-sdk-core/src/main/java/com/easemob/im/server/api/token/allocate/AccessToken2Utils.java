package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.exception.EMForbiddenException;
import com.easemob.im.server.exception.EMInvalidStateException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Map;
import java.util.function.Consumer;

public class AccessToken2Utils {

    private static final Logger log = LoggerFactory.getLogger(AccessToken2Utils.class);
    private static final String ERROR_MSG = "failed to build AccessToken2";

    public static int toExpireOnSeconds(int expireInSeconds) {
        return (int) (Instant.now().plusSeconds(expireInSeconds).toEpochMilli() / 1000);
    }

    public static Consumer<AccessToken2> rtcPrivilegeAdder(
            String channelName, String uid,
            AccessToken2.PrivilegeRtc privilegeRtc, int expireInSeconds) {
        int expireOnSeconds = toExpireOnSeconds(expireInSeconds);
        return token -> {
            AccessToken2.ServiceRtc serviceRtc = new AccessToken2.ServiceRtc(channelName, uid);
            serviceRtc.addPrivilegeRtc(privilegeRtc, expireOnSeconds);
            token.addService(serviceRtc);
        };
    }

    public static String buildAppToken(String appId, String appCert, int expireInSeconds) {
        int expireOnSeconds = toExpireOnSeconds(expireInSeconds);
        Instant expireDate = Instant.ofEpochSecond(expireOnSeconds);
        log.debug("buildingAppToken with expireInSeconds = {}, expireOnSeconds = {}, expireDate = {}",
                expireInSeconds, expireOnSeconds, expireDate.toString());

        AccessToken2 accessToken = new AccessToken2(appId, appCert, expireOnSeconds);
        AccessToken2.Service serviceChat = new AccessToken2.ServiceChat();
        serviceChat.addPrivilegeChat(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP, expireOnSeconds);
        accessToken.addService(serviceChat);
        try {
            return accessToken.build();
        } catch (Exception e) {
            log.error(ERROR_MSG, e);
            throw new EMInvalidStateException(ERROR_MSG);
        }
    }

    public static String buildUserChatToken(String appId, String appCert,
            String userId, int expireInSeconds) {
        int expireOnSeconds = toExpireOnSeconds(expireInSeconds);

        AccessToken2 accessToken = new AccessToken2(appId, appCert, expireOnSeconds);
        AccessToken2.Service serviceChat = new AccessToken2.ServiceChat(userId);
        serviceChat.addPrivilegeChat(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER, expireOnSeconds);
        accessToken.addService(serviceChat);

        try {
            return accessToken.build();
        } catch (Exception e) {
            log.error(ERROR_MSG, e);
            throw new EMInvalidStateException(ERROR_MSG);
        }
    }

    public static String buildUserCustomizedToken(String appId, String appCert, String userId,
            int expireInSeconds, Consumer<AccessToken2> tokenConfigurer) {
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

    // must include userId if it has the chat.user privilege
    // must not have chat.app privilege
    private static void validateUserChatToken(AccessToken2 token) {
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
