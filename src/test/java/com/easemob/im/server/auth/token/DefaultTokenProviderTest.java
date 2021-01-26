package com.easemob.im.server.auth.token;

import com.easemob.im.server.EMProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultTokenProviderTest {
    ObjectMapper json = new ObjectMapper();

    @Test
    public void testFetchAppToken() {
        MockingHttpServer server = new MockingHttpServer((req, rsp) -> {
            assertEquals("/easemob/demo/tokens", req.fullPath());
            String response = "{\"access_token\":\"access_token\",\"expires_in\": 3600}";
            ByteBuf responseBuf = Unpooled.wrappedBuffer(response.getBytes(Charset.forName("UTF-8")));
            return req.receive().aggregate().asByteArray()
                .map(bytes -> assertDoesNotThrow(() -> this.json.readValue(bytes, AppTokenRequest.class)))
                .doOnNext(request -> {
                    assertEquals("client_credentials", request.getGrantType());
                    assertEquals("id", request.getClientId());
                    assertEquals("secret", request.getClientSecret());
                }).then(rsp.send(Mono.just(responseBuf)).then());
        });

        EMProperties properties = EMProperties.builder()
            .withBaseUri(server.uri())
            .withAppkey("easemob#demo")
            .withClientId("id")
            .withClientSecret("secret")
            .build();

        HttpClient httpClient = HttpClient.create().baseUrl(properties.getBaseUri());
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(properties, httpClient, this.json);

        Token appToken = tokenProvider.fetchAppToken().block(Duration.ofSeconds(3));
        assertEquals("access_token", appToken.getValue());
        assertTrue(appToken.isValid());
    }

    @Test
    public void testFetchUserToken() {
        MockingHttpServer server = new MockingHttpServer((req, rsp) -> {
            assertEquals("/easemob/demo/tokens", req.fullPath());
            String response = "{\"access_token\":\"access_token\",\"expires_in\": 3600}";
            ByteBuf responseBuf = Unpooled.wrappedBuffer(response.getBytes(Charset.forName("UTF-8")));
            return req.receive().aggregate().asByteArray()
                .map(bytes -> assertDoesNotThrow(() -> this.json.readValue(bytes, UserTokenRequest.class)))
                .doOnNext(request -> {
                    assertEquals("password", request.getGrantType());
                    assertEquals("username", request.getUsername());
                    assertEquals("password", request.getPassword());
                }).then(rsp.send(Mono.just(responseBuf)).then());
        });

        EMProperties properties = EMProperties.builder()
            .withBaseUri(server.uri())
            .withAppkey("easemob#demo")
            .withClientId("id")
            .withClientSecret("secret")
            .build();

        HttpClient httpClient = HttpClient.create().baseUrl(properties.getBaseUri());
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(properties, httpClient, this.json);

        Token appToken = tokenProvider.fetchUserToken("username", "password").block(Duration.ofSeconds(3));
        assertEquals("access_token", appToken.getValue());
        assertTrue(appToken.isValid());
    }

}