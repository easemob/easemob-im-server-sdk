package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.exception.EMJsonException;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class DefaultTokenProvider implements TokenProvider, UserTokenProvider {

    private final EMProperties properties;

    private final HttpClient http;

    private final ObjectMapper json;

    private Mono<EMToken> appToken;

    public DefaultTokenProvider(EMProperties properties, HttpClient http, ObjectMapper json) {
        this.properties = properties;
        this.http = http;
        this.json = json;
        initialize();
    }

    @Override
    public Mono<EMToken> fetchAppToken() {
        return this.appToken;
    }

    @Override
    public Mono<EMToken> fetchUserToken(String username, String password) {
        return fetchToken(UserTokenRequest.of(username, password));
    }

    private void initialize() {
        AppTokenRequest appTokenRequest = AppTokenRequest.of(this.properties.getClientId(), this.properties.getClientSecret());
        this.appToken = fetchToken(appTokenRequest).flatMap(token -> {
            Duration ttl = Duration.between(Instant.now(), token.getExpireTimestamp());
            if (ttl.isNegative()) {
                // TODO: logging
                System.out.println("token already expired");
                return Mono.error(new EMInvalidStateException("token already expired"));
            }
            return Mono.just(token).cache(ttl.dividedBy(2));
        });
        // TODO: logging
        System.out.println("DefaultTokenProvider initialized");
    }

    private Mono<EMToken> fetchToken(TokenRequest tokenRequest) {
        ByteBuf tokenRequestBuffer = Unpooled.buffer();
        try {
            byte[] bytes = this.json.writeValueAsBytes(tokenRequest);
            tokenRequestBuffer.writeBytes(bytes);
        } catch (JsonProcessingException e) {
            throw new EMJsonException(String.format("could not serialize token request: %s", e.getMessage()));
        }

        return this.http.request(HttpMethod.POST)
            .uri("/tokens")
            .send(Mono.just(tokenRequestBuffer))
            .responseSingle((resp, buf) -> {
                if (resp.status().equals(HttpResponseStatus.OK)) {
                    return buf.asByteArray();
                } else {
                    // TODO: replace System.out.println with logging
                    System.out.println(String.format("failed to request token, server replied: %s", resp.status().toString()));
                    // TODO: Need some error mapper here
                    return Mono.error(new EMUnknownException(String.format("failed to request token, server replied: %s", resp.status().toString())));
                }
            }).map(bytes -> {
                try {
                    return this.json.readValue(bytes, TokenResponse.class);
                } catch (IOException e) {
                    System.out.println(String.format("could not deserialize token response: %s", e.getMessage()));
                    throw new EMUnknownException(String.format("could not deserialize token response: %s", e.getMessage()));
                }
            }).map(TokenResponse::asToken);
    }
}
