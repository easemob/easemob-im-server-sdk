package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.api.token.allocate.AccessToken2Utils;
import com.easemob.im.server.api.token.allocate.AgoraTokenProvider;
import com.easemob.im.server.api.token.allocate.ExchangeTokenRequest;
import com.easemob.im.server.api.token.allocate.TokenResponse;
import com.easemob.im.server.api.user.get.UserGetResponse;
import com.easemob.im.server.exception.EMUnauthorizedException;
import com.easemob.im.server.model.EMUser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AgoraTokenIT {

    private static final AccessToken2.PrivilegeRtc DUMMY_RTC_PRIVILEGE =
            AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL;
    private static final String DUMMY_CHANNEL_NAME = "dummyChannelName";
    private static final String DUMMY_UID = "dummyUID";
    private static final int EXPIRE_IN_SECONDS = 600;
    private static final int REQUEST_TIMEOUT = 10;

    private static final Logger log = LoggerFactory.getLogger(AgoraTokenProvider.class);
    private static final String ALICE_USER_NAME = "yifan3";
    private static final String BOB_USER_NAME = "ken-0";

    private final ExchangeTokenRequest exchangeTokenRequest = new ExchangeTokenRequest();

    protected EMService service;
    HttpClient aliceEasemobTokenClient = null;

    String appkey = System.getenv("IM_APPKEY");
    String baseUri = System.getenv("IM_BASE_URI");
    String appId = System.getenv("IM_APP_ID");
    String appCert = System.getenv("IM_APP_CERT");

    public AgoraTokenIT() {
        EMProperties properties = EMProperties.agoraRealmBuilder()
                .setBaseUri(baseUri)
                .setAppkey(appkey)
                .setAppId(appId)
                .setAppCert(appCert)
                .build();
        this.service = new EMService(properties);
    }

    // With an Easemob App Token you can GET all users
    @Test
    public void appTokenTest() {
        assertDoesNotThrow(() -> this.service.user()
                .listUsers(1, null).block(Duration.ofSeconds(REQUEST_TIMEOUT)));
    }

    @Test
    public void userTokenTest() throws Exception {
        String aliceId = service.user().getUUID(ALICE_USER_NAME).block(Duration.ofSeconds(REQUEST_TIMEOUT));
        String aliceAgoraToken = service.user().getToken(aliceId, EXPIRE_IN_SECONDS,
            AccessToken2Utils.rtcPrivilegeAdder(DUMMY_CHANNEL_NAME, DUMMY_UID,
                    DUMMY_RTC_PRIVILEGE, EXPIRE_IN_SECONDS)
        ).block(Duration.ofSeconds(REQUEST_TIMEOUT)).getValue();
        log.debug("aliceAgoraToken = {}", aliceAgoraToken);
        String aliceEasemobToken = toEasemobToken(aliceAgoraToken);
        log.debug("aliceEasemobToken = {}", aliceEasemobToken);

        // With an Easemob User Token you are not authorized to GET another user
        assertThrows(EMUnauthorizedException.class, () -> {
            EMUser bobUser = aliceEasemobTokenClient
                    .get().uri(String.format("/users/%s", BOB_USER_NAME))
                    .responseSingle((rsp, buf) -> service.getContext().getErrorMapper().apply(rsp)
                            .then(buf))
                    .map(buf -> service.getContext().getCodec().decode(buf, UserGetResponse.class))
                    .block(Duration.ofSeconds(REQUEST_TIMEOUT))
                    .getEMUser(BOB_USER_NAME);
            log.debug("bobUser = {}", bobUser.toString());
        });

        // With an Easemob User Token you are able to GET the same user
        assertDoesNotThrow(() -> {
            EMUser aliceUser = aliceEasemobTokenClient
                    .get().uri(String.format("/users/%s", ALICE_USER_NAME))
                    .responseSingle((rsp, buf) -> service.getContext().getErrorMapper().apply(rsp)
                            .then(buf))
                    .map(buf -> service.getContext().getCodec().decode(buf, UserGetResponse.class))
                    .block(Duration.ofSeconds(REQUEST_TIMEOUT))
                    .getEMUser(ALICE_USER_NAME);
            log.debug("aliceUser = {}", aliceUser.toString());
        });
    }

    private String toEasemobToken(String agoraToken) {
        Context context = service.getContext();
        HttpClient httpClient = EMHttpClientFactory.create(context.getProperties());
        String baseUrl =
                String.format("%s/%s", baseUri, context.getProperties().getAppkeySlashDelimited());
        HttpClient aliceAgoraTokenClient = httpClient.baseUrl(baseUrl).headers(
                headers -> headers.set("Authorization", String.format("Bearer %s", agoraToken)));
        String easemobToken = aliceAgoraTokenClient
                .post().uri("/token")
                .send(Mono.create(sink -> sink
                        .success(context.getCodec().encode(exchangeTokenRequest))))
                .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
                .map(buf -> context.getCodec().decode(buf, TokenResponse.class))
                .map(TokenResponse::asToken).block().getValue();

        HttpClient httpClient2 = EMHttpClientFactory.create(context.getProperties());
        aliceEasemobTokenClient = httpClient2.baseUrl(baseUrl).headers(
                headers -> headers.set("Authorization", String.format("Bearer %s", easemobToken)));

        return easemobToken;
    }
}
