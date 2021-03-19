package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.loadbalance.Endpoint;
import com.easemob.im.server.api.loadbalance.EndpointRegistry;
import com.easemob.im.server.api.loadbalance.LoadBalancer;
import com.easemob.im.server.api.token.Token;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultTokenProviderTest extends AbstractApiTest {

    public DefaultTokenProviderTest() {
        this.server.addHandler("POST /easemob/demo/token", this::handlePostToken);
    }

    private DefaultTokenProvider tokenProvider;

    @BeforeEach
    void init() {
        HttpClient httpClient = HttpClient.newConnection();
        LoadBalancer loadBalancer = endpoints -> endpoints.get(0);
        EndpointRegistry endpointRegistry = () -> Arrays.asList(new Endpoint("http", "localhost", this.server.port()));
        this.tokenProvider = new DefaultTokenProvider(this.context.getProperties(), httpClient, endpointRegistry, loadBalancer, this.context.getCodec(), this.context.getErrorMapper());
    }

    @Test
    public void testFetchAppToken() {
        Token appToken = this.tokenProvider.fetchAppToken().block(Duration.ofSeconds(3));
        assertEquals("access_token", appToken.getValue());
        assertTrue(appToken.isValid());
    }

    private JsonNode handlePostToken(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode()
            .put("access_token", "access_token")
            .put("expires_in", 3600);
    }

    @Test
    public void testFetchUserToken() {
        Token appToken = this.tokenProvider.fetchUserToken("username", "password").block(Duration.ofSeconds(3));
        assertEquals("access_token", appToken.getValue());
        assertTrue(appToken.isValid());
    }

}