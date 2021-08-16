package com.easemob.im.server.api.metadata;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.api.token.allocate.TokenRequest;
import com.easemob.im.server.api.token.allocate.TokenResponse;
import com.easemob.im.server.api.token.allocate.UserTokenRequest;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMForbiddenException;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.model.EMUser;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

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

    /**
     * TODO: this doc is outdated
     * 获取 User Token
     * <p>
     * Token 的结构请参考 {@code AccessToken2}
     * <p>
     * Token 中除了 AgoraChat 权限以外, 还可以自定义添加其他 Agora 服务(比如RTC)的权限,
     * 对每个服务的权限可以独立配置不同的过期时间.
     * <p>
     * 为用户 Alice 生成仅含 AgoraChat 权限的 UserToken, 有效期为3600秒:
     * <pre><code>
     * EMUser alice = new EMUser("alice", "da920000-ecf9-11eb-9af3-296ff79acb67", true);
     * String aliceAgoraChatToken = service.token().generateUserToken(alice, 3600, token -&gt; {});
     * </code></pre>
     * <p>
     * 为用户 Bob 生成包含 AgoraChat 权限和 AgoraRTC (JOIN_CHANNEL) 权限的 UserToken, 有效期为600秒:
     * <pre><code>
     * // please pay attention to expireInSeconds (Duration) vs. expireOnSeconds (Instant)
     * int expireInSeconds = 600;
     * int expireOnSeconds = Utilities.toExpireOnSeconds(expireInSeconds);
     * EMUser bob = new EMUser("bob", "da921111-ecf9-11eb-9af3-296ff79acb67", true);
     * String bobAgoraChatRtcToken = service.token().generateUserToken(bob, expireInSeconds, token -&gt; {
     *     AccessToken2.ServiceRtc serviceRtc = new AccessToken2.ServiceRtc("dummyRtcChannelName", uid);
     *     serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL, expireOnSeconds);
     *     token.addService(serviceRtc);
     * });
     * </code></pre>
     *
     * @param user 用户
     * @param expireInSeconds token 过期时间 TTL in seconds
     * @param tokenConfigurer 用来自定义添加其他 Agora 服务的 lambda function
     * @return Agora Token
     */
    public String getUserToken(EMUser user, Integer expireInSeconds,
            Consumer<AccessToken2> tokenConfigurer, String password) {
        EMProperties properties = context.getProperties();
        EMProperties.Realm realm = properties.getRealm();
        if (realm.equals(EMProperties.Realm.AGORA_REALM)) {
            String userId = user.getUuid();
            String appId = properties.getAppId();
            String appCert = properties.getAppCert();
            int expireOnSeconds = Utilities.toExpireOnSeconds(expireInSeconds);

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
        } else if (realm.equals(EMProperties.Realm.EASEMOB_REALM)) {
            if (Strings.isBlank(password)) {
                throw new EMInvalidArgumentException("password is missing");
            }
            String userName = user.getUsername();
            return fetchUserTokenWithEasemobRealm(this.context, UserTokenRequest.of(userName, password))
                    .map(Token::getValue).block(Utilities.IT_TIMEOUT);
        } else {
            throw new EMInvalidStateException(String.format("invalid realm value %s", realm));
        }
    }

    public static Mono<Token> fetchUserTokenWithEasemobRealm(Context context, TokenRequest tokenRequest) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/token")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(tokenRequest))))
                        .responseSingle(
                                (rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf)))
                .map(buf -> context.getCodec().decode(buf, TokenResponse.class))
                .map(TokenResponse::asToken);
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
