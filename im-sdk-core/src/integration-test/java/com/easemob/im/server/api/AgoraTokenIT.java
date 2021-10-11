package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.api.token.allocate.AgoraTokenProvider;
import com.easemob.im.server.api.user.get.UserGetResponse;
import com.easemob.im.server.exception.EMUnauthorizedException;
import com.easemob.im.server.model.EMUser;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import com.easemob.im.server.api.util.Utilities;

import static com.easemob.im.server.api.util.Utilities.IT_TIMEOUT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AgoraTokenIT {

    private static final AccessToken2.PrivilegeRtc DUMMY_RTC_PRIVILEGE =
            AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL;
    private static final String DUMMY_CHANNEL_NAME = "dummyChannelName";
    private static final String DUMMY_UID = "dummyUID";

    private static final int USER_TOKEN_EXPIRE_IN_SECONDS = 600;
    private static final int APP_TOKEN_EXPIRE_IN_SECONDS = 10;

    private static final Logger log = LoggerFactory.getLogger(AgoraTokenProvider.class);
    protected EMService service;

    String realm = System.getenv("IM_REALM");
    String appkey = System.getenv("IM_APPKEY");
    String baseUri = System.getenv("IM_BASE_URI");
    String appId = System.getenv("IM_APP_ID");
    String appCert = System.getenv("IM_APP_CERT");

    @BeforeAll
    public void init() {
        Assumptions.assumeTrue(EMProperties.Realm.AGORA_REALM.name().equals(realm));
        EMProperties properties = EMProperties.builder()
                .setAgoraTokenExpireInSeconds(APP_TOKEN_EXPIRE_IN_SECONDS)
                .setRealm(EMProperties.Realm.AGORA_REALM)
                .setBaseUri(baseUri)
                .setAppkey(appkey)
                .setAppId(appId)
                .setAppCert(appCert)
                .build();
        this.service = new EMService(properties);
    }

    // With an Easemob App Token you can GET all users
    // The app token will renew upon expiration
    @Test
    public void appTokenTest() throws InterruptedException {
        for (int i = 0; i < 20; i ++) {
            assertDoesNotThrow(() -> this.service.user()
                    .listUsers(1, null).block(Utilities.IT_TIMEOUT));
            Thread.sleep(1000);
        }
    }

    @Test
    public void userTokenTest() throws Exception {

        String aliceUserName = Utilities.randomUserName();
        String alicePassword = Utilities.randomPassword();
        String bobUserName = Utilities.randomUserName();
        String bobPassword = Utilities.randomPassword();
        service.user().create(aliceUserName, alicePassword).block(IT_TIMEOUT);
        service.user().create(bobUserName, bobPassword).block(IT_TIMEOUT);

        EMUser aliceUser = service.user().get(aliceUserName).block(Utilities.IT_TIMEOUT);
        String aliceAgoraToken = service.token().getUserToken(aliceUser,
                USER_TOKEN_EXPIRE_IN_SECONDS,
                token -> {
                    AccessToken2.ServiceRtc serviceRtc =
                            new AccessToken2.ServiceRtc(DUMMY_CHANNEL_NAME, DUMMY_UID);
                    serviceRtc.addPrivilegeRtc(DUMMY_RTC_PRIVILEGE, USER_TOKEN_EXPIRE_IN_SECONDS);
                    token.addService(serviceRtc);
                },
                null
        );

        HttpClient clientWithAliceEasemobToken = getClientWithEasemobHeader(aliceAgoraToken);

        // With an Easemob User Token you are not authorized to GET another user
        assertThrows(EMUnauthorizedException.class, () -> {
            EMUser bobUser = clientWithAliceEasemobToken
                    .get().uri(String.format("/users/%s", bobUserName))
                    .responseSingle((rsp, buf) -> {
                        service.getContext().getErrorMapper().statusCode(rsp);
                        return buf;
                    })
                    .doOnNext(buf -> service.getContext().getErrorMapper().checkError(buf))
                    .map(buf -> service.getContext().getCodec().decode(buf, UserGetResponse.class))
                    .block(Utilities.IT_TIMEOUT)
                    .getEMUser(bobUserName);
            log.debug("bobUser = {}", bobUser.toString());
        });

        // With an Easemob User Token you are able to GET the token owner
        assertDoesNotThrow(() -> {
            EMUser aliceUserFetchedWithHerToken = clientWithAliceEasemobToken
                    .get().uri(String.format("/users/%s", aliceUserName))
                    .responseSingle((rsp, buf) -> {
                        service.getContext().getErrorMapper().statusCode(rsp);
                        return buf;
                    })
                    .doOnNext(buf -> service.getContext().getErrorMapper().checkError(buf))
                    .map(buf -> service.getContext().getCodec().decode(buf, UserGetResponse.class))
                    .block(Utilities.IT_TIMEOUT)
                    .getEMUser(aliceUserName);
            log.debug("aliceUser = {}", aliceUserFetchedWithHerToken.toString());
        });

        service.user().delete(aliceUserName).block(IT_TIMEOUT);
        service.user().delete(bobUserName).block(IT_TIMEOUT);
    }

    private HttpClient getClientWithEasemobHeader(String agoraToken) {
        Context context = service.getContext();
        Codec codec = context.getCodec();
        ErrorMapper errorMapper = context.getErrorMapper();
        HttpClient httpClient = EMHttpClientFactory.create(context.getProperties());
        String baseUrl =
                String.format("%s/%s", baseUri, context.getProperties().getAppkeySlashDelimited());
        String easemobToken = AgoraTokenProvider
                .exchangeForEasemobToken(httpClient, baseUrl, Mono.just(agoraToken), codec, errorMapper)
                .block(IT_TIMEOUT).getValue();
        return EMHttpClientFactory.create(context.getProperties()).baseUrl(baseUrl)
                .headers(headers -> headers
                        .set("Authorization", String.format("Bearer %s", easemobToken)));
    }
}
