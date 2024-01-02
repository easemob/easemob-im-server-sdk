package com.easemob.im.server.api.token;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.api.token.allocate.*;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.*;
import com.easemob.im.server.model.EMUser;
import io.netty.util.internal.StringUtil;
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

    public static Mono<Token> fetchUserTokenWithEasemobRealm(Context context,
            TokenRequest tokenRequest) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/token")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(tokenRequest))))
                        .responseSingle((rsp, buf) -> {
                            return buf.switchIfEmpty(
                                            Mono.error(new EMUnknownException("response is null")))
                                    .flatMap(byteBuf -> {
                                        ErrorMapper mapper = new DefaultErrorMapper();
                                        mapper.statusCode(rsp);
                                        mapper.checkError(byteBuf);
                                        return Mono.just(byteBuf);
                                    });
                        }))
                .map(buf -> context.getCodec().decode(buf, TokenResponse.class))
                .map(TokenResponse::asToken);
    }

    /**
     * 获取 App Token
     * @return token
     */
    public Mono<Token> getAppToken() {
        return this.context.getTokenProvider().fetchAppToken();
    }

    /**
     * 获取 User Token，expireInSeconds 仅对生成 Agora userToken 生效
     * <p>
     * 可获取 Easemob userToken 或 Agora userToken.
     * <p>
     * 如果您在初始化 EMProperties 时使用的是 ClientId 与 ClientSecret，那么无需关注 Agora userToken，直接使用以下示例获取 Easemob userToken 即可。
     * 为用户 Cat 获取 Easemob userToken
     * <pre>{@code
     * EMUser cathy = new EMUser("cathy", "da920000-ecf9-11eb-9af3-296ff79acb67", true);
     * String cathyEasemobToken = service.token().getUserToken(cathy, null, null, "passwordOfUserCat");
     * }</pre>
     * <p>
     * 如果您在初始化 EMProperties 时使用的是 AppId 与 AppCert，那么需要关注 Agora userToken。
     * 其中 Agora userToken 的结构请参考 {@link AccessToken2}
     * <p>
     * Agora userToken 中除了 AgoraChat 权限以外, 还可以自定义添加其他 Agora 服务(比如RTC)的权限,
     * 对每个服务的权限可以单独设置不同的过期时间.
     * <p>
     * 为用户 Alice 生成仅含 AgoraChat 权限的 Agora userToken, 有效期为3600秒:
     * <pre>{@code
     * EMUser alice = new EMUser("alice", "da920000-ecf9-11eb-9af3-296ff79acb67", true);
     * String aliceAgoraChatToken = service.token().getUserToken(alice, 3600, null, null);
     * }</pre>
     * <p>
     * 为用户 Bob 生成包含 AgoraChat 权限和 AgoraRTC (JOIN_CHANNEL) 权限的 Agora userToken, 有效期为600秒:
     * <pre>{@code
     *
     * EMUser bob = new EMUser("bob", "da921111-ecf9-11eb-9af3-296ff79acb67", true);
     * String bobAgoraChatRtcToken = service.token().getUserToken(bob, 600, token -> {
     *     AccessToken2.ServiceRtc serviceRtc = new AccessToken2.ServiceRtc("dummyRtcChannelName", "dummyUid");
     *     serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL, 600);
     *     token.addService(serviceRtc);
     * }, null);
     * }</pre>
     *
     * @param user            用户
     * @param expireInSeconds token 过期时间 TTL in seconds，该参数仅对 Agora userToken 生效
     * @param tokenConfigurer 用来自定义添加其他 Agora 服务的 lambda function
     * @param password        用户密码
     * @return Easemob userToken 或 Agora userToken
     */
    public String getUserToken(EMUser user, Integer expireInSeconds,
            Consumer<AccessToken2> tokenConfigurer, String password) {

        if (user == null) {
            throw new EMInvalidArgumentException("user cannot be null");
        }
        EMProperties properties = context.getProperties();
        EMProperties.Realm realm = properties.getRealm();
        if (realm.equals(EMProperties.Realm.AGORA_REALM)) {
            if (expireInSeconds == null) {
                throw new EMInvalidArgumentException("expireInSeconds cannot be null");
            }
            String userId = user.getUuid();
            String appId = properties.getAppId();
            String appCert = properties.getAppCert();

            AccessToken2 accessToken = new AccessToken2(appId, appCert, expireInSeconds);
            AccessToken2.Service serviceChat = new AccessToken2.ServiceChat(userId);
            serviceChat.addPrivilegeChat(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER,
                    expireInSeconds);
            accessToken.addService(serviceChat);

            if (tokenConfigurer != null) {
                tokenConfigurer.accept(accessToken);
            }
            validateUserChatToken(accessToken);

            try {
                return accessToken.build();
            } catch (Exception e) {
                log.error(ERROR_MSG, e);
                throw new EMInvalidStateException(ERROR_MSG);
            }
        } else if (realm.equals(EMProperties.Realm.EASEMOB_REALM)) {
            if (StringUtil.isNullOrEmpty(password)) {
                throw new EMInvalidArgumentException("password cannot be blank");
            }
            String userName = user.getUsername();
            return fetchUserTokenWithEasemobRealm(this.context,
                    UserTokenRequest.of(userName, password))
                    .map(Token::getValue).block(Utilities.IT_TIMEOUT);
        } else {
            throw new EMInvalidStateException(String.format("invalid realm value %s", realm));
        }
    }

    /**
     * 获取 User Token，expireInSeconds 对生成 Easemob userToken 和 Agora userToken 都会生效
     * <p>
     * 可获取 Easemob userToken 或 Agora userToken.
     * <p>
     * 如果您在初始化 EMProperties 时使用的是 ClientId 与 ClientSecret，那么无需关注 Agora userToken，直接使用以下示例获取 Easemob userToken 即可。
     * 为用户 Cat 获取 Easemob userToken
     * <pre>{@code
     * EMUser cathy = new EMUser("cathy", "da920000-ecf9-11eb-9af3-296ff79acb67", true);
     * String cathyEasemobToken = service.token().getUserTokenWithTtl(cathy, 86400, null, "passwordOfUserCat");
     * }</pre>
     * <p>
     * 如果您在初始化 EMProperties 时使用的是 AppId 与 AppCert，那么需要关注 Agora userToken。
     * 其中 Agora userToken 的结构请参考 {@link AccessToken2}
     * <p>
     * Agora userToken 中除了 AgoraChat 权限以外, 还可以自定义添加其他 Agora 服务(比如RTC)的权限,
     * 对每个服务的权限可以单独设置不同的过期时间.
     * <p>
     * 为用户 Alice 生成仅含 AgoraChat 权限的 Agora userToken, 有效期为3600秒:
     * <pre>{@code
     * EMUser alice = new EMUser("alice", "da920000-ecf9-11eb-9af3-296ff79acb67", true);
     * String aliceAgoraChatToken = service.token().getUserTokenWithTtl(alice, 3600, null, null);
     * }</pre>
     * <p>
     * 为用户 Bob 生成包含 AgoraChat 权限和 AgoraRTC (JOIN_CHANNEL) 权限的 Agora userToken, 有效期为600秒:
     * <pre>{@code
     *
     * EMUser bob = new EMUser("bob", "da921111-ecf9-11eb-9af3-296ff79acb67", true);
     * String bobAgoraChatRtcToken = service.token().getUserTokenWithTtl(bob, 600, token -> {
     *     AccessToken2.ServiceRtc serviceRtc = new AccessToken2.ServiceRtc("dummyRtcChannelName", "dummyUid");
     *     serviceRtc.addPrivilegeRtc(AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL, 600);
     *     token.addService(serviceRtc);
     * }, null);
     * }</pre>
     *
     * @param user            用户
     * @param expireInSeconds token 过期时间 TTL in seconds
     * @param tokenConfigurer 用来自定义添加其他 Agora 服务的 lambda function
     * @param password        用户密码
     * @return Easemob userToken 或 Agora userToken
     */
    public String getUserTokenWithTtl(EMUser user, Integer expireInSeconds,
            Consumer<AccessToken2> tokenConfigurer, String password) {

        if (user == null) {
            throw new EMInvalidArgumentException("user cannot be null");
        }
        EMProperties properties = context.getProperties();
        EMProperties.Realm realm = properties.getRealm();
        if (realm.equals(EMProperties.Realm.AGORA_REALM)) {
            if (expireInSeconds == null) {
                throw new EMInvalidArgumentException("expireInSeconds cannot be null");
            }
            String userId = user.getUuid();
            String appId = properties.getAppId();
            String appCert = properties.getAppCert();

            AccessToken2 accessToken = new AccessToken2(appId, appCert, expireInSeconds);
            AccessToken2.Service serviceChat = new AccessToken2.ServiceChat(userId);
            serviceChat.addPrivilegeChat(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER,
                    expireInSeconds);
            accessToken.addService(serviceChat);

            if (tokenConfigurer != null) {
                tokenConfigurer.accept(accessToken);
            }
            validateUserChatToken(accessToken);

            try {
                return accessToken.build();
            } catch (Exception e) {
                log.error(ERROR_MSG, e);
                throw new EMInvalidStateException(ERROR_MSG);
            }
        } else if (realm.equals(EMProperties.Realm.EASEMOB_REALM)) {
            if (StringUtil.isNullOrEmpty(password)) {
                throw new EMInvalidArgumentException("password cannot be blank");
            }
            String userName = user.getUsername();
            return fetchUserTokenWithEasemobRealm(this.context,
                    UserTokenTtlRequest.of(userName, password, Long.valueOf(expireInSeconds)))
                    .map(Token::getValue).block(Utilities.IT_TIMEOUT);
        } else {
            throw new EMInvalidStateException(String.format("invalid realm value %s", realm));
        }
    }

    public String getUserTokenWithInherit(String username) {
        if (username == null || username.isEmpty()) {
            throw new EMInvalidArgumentException("user cannot be null");
        }
        EMProperties properties = context.getProperties();
        EMProperties.Realm realm = properties.getRealm();
        if (realm.equals(EMProperties.Realm.AGORA_REALM)) {
            throw new EMNotImplementedException("get user token with agora realm not implemented");
        } else if (realm.equals(EMProperties.Realm.EASEMOB_REALM)) {
            return fetchUserTokenWithEasemobRealm(this.context,
                    InheritTokenRequest.of(username, true))
                    .map(Token::getValue).block(Utilities.IT_TIMEOUT);
        } else {
            throw new EMInvalidStateException(String.format("invalid realm value %s", realm));
        }
    }

    public String getUserTokenWithInherit(String username, Integer expireInSeconds) {
        if (username == null || username.isEmpty()) {
            throw new EMInvalidArgumentException("user cannot be null");
        }
        EMProperties properties = context.getProperties();
        EMProperties.Realm realm = properties.getRealm();
        if (realm.equals(EMProperties.Realm.AGORA_REALM)) {
            throw new EMNotImplementedException("get user token with agora realm not implemented");
        } else if (realm.equals(EMProperties.Realm.EASEMOB_REALM)) {
            return fetchUserTokenWithEasemobRealm(this.context,
                    InheritTokenTtlRequest.of(username, true, Long.valueOf(expireInSeconds)))
                    .map(Token::getValue).block(Utilities.IT_TIMEOUT);
        } else {
            throw new EMInvalidStateException(String.format("invalid realm value %s", realm));
        }
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
        boolean hasUserId = StringUtil.isNullOrEmpty(userId);
        boolean hasAppPrivilege =
                chatPrivileges.get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP.intValue) != null;
        boolean hasUserPrivilege =
                chatPrivileges.get(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_USER.intValue) != null;
        if (hasAppPrivilege) {
            throw new EMForbiddenException("userToken must not have chat app privilege");
        }
        if (hasUserPrivilege && !hasUserId) {
            throw new EMForbiddenException(
                    "accessToken with the chatUser privilege must include an userId");
        }
    }

}
