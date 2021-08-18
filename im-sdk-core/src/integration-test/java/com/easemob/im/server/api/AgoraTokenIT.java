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
import reactor.netty.http.client.HttpClient;
import com.easemob.im.server.api.util.Utilities;

import static com.easemob.im.server.api.util.Utilities.IT_TIMEOUT;
import static com.easemob.im.server.api.util.Utilities.toExpireOnSeconds;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AgoraTokenIT {

    private static final AccessToken2.PrivilegeRtc DUMMY_RTC_PRIVILEGE =
            AccessToken2.PrivilegeRtc.PRIVILEGE_JOIN_CHANNEL;
    private static final String DUMMY_CHANNEL_NAME = "dummyChannelName";
    private static final String DUMMY_UID = "dummyUID";
    private static final int EXPIRE_IN_SECONDS = 600;

    private static final Logger log = LoggerFactory.getLogger(AgoraTokenProvider.class);
    // this name must be yifan3 for now
    // TODO: dont hardcode names once easemobUserName -> agoraUserId mapping is ready
    private static final String ALICE_USER_NAME = "yifan3";
    private static final String BOB_USER_NAME = "ken-0";

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
                .setRealm(EMProperties.Realm.AGORA_REALM)
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
                .listUsers(1, null).block(Utilities.IT_TIMEOUT));
    }

    @Test
    public void userTokenTest() throws Exception {
        EMUser aliceUser = service.user().get(ALICE_USER_NAME).block(Utilities.IT_TIMEOUT);
        String aliceAgoraToken = service.token().getUserToken(aliceUser, EXPIRE_IN_SECONDS,
                token -> {
                    AccessToken2.ServiceRtc serviceRtc = new AccessToken2.ServiceRtc(DUMMY_CHANNEL_NAME, DUMMY_UID);
                    serviceRtc.addPrivilegeRtc(DUMMY_RTC_PRIVILEGE, toExpireOnSeconds(EXPIRE_IN_SECONDS));
                    token.addService(serviceRtc);
                },
                null
        );

        HttpClient clientWithAliceEasemobToken = getClientWithEasemobHeader(aliceAgoraToken);

        // With an Easemob User Token you are not authorized to GET another user
        assertThrows(EMUnauthorizedException.class, () -> {
            EMUser bobUser = clientWithAliceEasemobToken
                    .get().uri(String.format("/users/%s", BOB_USER_NAME))
                    .responseSingle((rsp, buf) -> service.getContext().getErrorMapper().apply(rsp)
                            .then(buf))
                    .map(buf -> service.getContext().getCodec().decode(buf, UserGetResponse.class))
                    .block(Utilities.IT_TIMEOUT)
                    .getEMUser(BOB_USER_NAME);
            log.debug("bobUser = {}", bobUser.toString());
        });

        // With an Easemob User Token you are able to GET the token owner
        assertDoesNotThrow(() -> {
            EMUser aliceUserFetchedWithHerToken = clientWithAliceEasemobToken
                    .get().uri(String.format("/users/%s", ALICE_USER_NAME))
                    .responseSingle((rsp, buf) -> service.getContext().getErrorMapper().apply(rsp)
                            .then(buf))
                    .map(buf -> service.getContext().getCodec().decode(buf, UserGetResponse.class))
                    .block(Utilities.IT_TIMEOUT)
                    .getEMUser(ALICE_USER_NAME);
            log.debug("aliceUser = {}", aliceUserFetchedWithHerToken.toString());
        });
    }

    private HttpClient getClientWithEasemobHeader(String agoraToken) {
        Context context = service.getContext();
        Codec codec = context.getCodec();
        ErrorMapper errorMapper = context.getErrorMapper();
        HttpClient httpClient = EMHttpClientFactory.create(context.getProperties());
        String baseUrl = String.format("%s/%s", baseUri, context.getProperties().getAppkeySlashDelimited());
        String easemobToken = AgoraTokenProvider.exchangeForEasemobToken(httpClient, baseUrl, agoraToken, codec, errorMapper)
                .block(IT_TIMEOUT).getValue();
        return EMHttpClientFactory.create(context.getProperties()).baseUrl(baseUrl)
                .headers(headers -> headers.set("Authorization", String.format("Bearer %s", easemobToken)));
    }
}
