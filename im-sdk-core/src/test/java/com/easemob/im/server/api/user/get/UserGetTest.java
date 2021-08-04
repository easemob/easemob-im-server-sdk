package com.easemob.im.server.api.user.get;

import com.easemob.im.server.EMProxy;
import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.net.InetSocketAddress;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserGetTest extends AbstractApiTest {

    private static final String DUMMY_USER_NAME = "dummy-user-name";
    private static final String DUMMY_USER_UUID = "b2832810-e91f-11eb-901b-1d2efade27a3";
    private static final boolean DUMMY_USER_ACTIVATED = true;

    private UserGet userGet;

    public UserGetTest() {
        this.server.addHandler(String.format("GET /easemob/demo/users/%s", DUMMY_USER_NAME), this::handleUserGetSingle);
        this.userGet = new UserGet(this.context);
    }

    @Test
    public void testUserGetSingle() {
        EMUser user = this.userGet.single(DUMMY_USER_NAME).block(Duration.ofSeconds(3));
        assertEquals(DUMMY_USER_NAME, user.getUsername());
        assertEquals(DUMMY_USER_UUID, user.getUuid());
        assertEquals(DUMMY_USER_ACTIVATED, user.getCanLogin());
    }

    @Test
    public void testUserGetSingleUsingHttpClientProxy() {
        EMProxy proxyInfo = EMProxy.builder()
                .setIP("127.0.0.1")
                .setPort(18888)
                .build();
        HttpClient httpClient = this.context.getHttpClient().block();
        this.context.setHttpClient(
                Mono.just(httpClient.proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
                        .address(new InetSocketAddress(proxyInfo.getIp(), proxyInfo.getPort())))));

        assertThrows(RuntimeException.class,
                () -> this.userGet.single("username").block(Duration.ofSeconds(3)));
    }

    private JsonNode handleUserGetSingle(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", DUMMY_USER_NAME);
        user.put("uuid", DUMMY_USER_UUID);
        user.put("activated", DUMMY_USER_ACTIVATED);

        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);

        return rsp;
    }
}
