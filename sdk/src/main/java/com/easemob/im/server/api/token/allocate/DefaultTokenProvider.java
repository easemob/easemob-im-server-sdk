package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.ErrorMapper;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class DefaultTokenProvider implements TokenProvider, UserTokenProvider {
    private static final Logger LOG = LogManager.getLogger();

    private EMProperties properties;

    private HttpClient httpClient;

    private Codec codec;

    private ErrorMapper errorMapper;

    private Mono<EMToken> appToken;

    public DefaultTokenProvider(EMProperties properties, HttpClient httpClient, Codec codec, ErrorMapper errorMapper) {
        this.properties = properties;
        this.httpClient = httpClient;
        this.codec = codec;
        this.errorMapper = errorMapper;
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
        this.appToken = fetchToken(appTokenRequest)
                .cache(token -> Duration.between(Instant.now(), token.getExpireTimestamp()).dividedBy(2),
                        error -> Duration.ofSeconds(10),
                        () -> Duration.ofSeconds(10));

        LOG.info("token provider initialized");
    }

    private Mono<EMToken> fetchToken(TokenRequest tokenRequest) {
        return this.httpClient
            .post()
            .uri("/token")
            .send(Mono.create(sink -> sink.success(this.codec.encode(tokenRequest))))
            .responseSingle((rsp, buf) -> this.errorMapper.apply(rsp).then(buf))
            .map(buf -> this.codec.decode(buf, TokenResponse.class))
            .map(TokenResponse::asToken);
    }
}
