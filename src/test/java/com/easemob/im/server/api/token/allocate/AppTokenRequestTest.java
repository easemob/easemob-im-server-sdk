package com.easemob.im.server.api.token.allocate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTokenRequestTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testAppTokenSerialized() throws JsonProcessingException {
        AppTokenRequest appTokenRequest = AppTokenRequest.of("id", "secret");
        String encoded = this.mapper.writeValueAsString(appTokenRequest);
        JsonNode decoded = this.mapper.readTree(encoded);
        assertEquals("client_credentials", decoded.get("grant_type").asText());
        assertEquals("id", decoded.get("client_id").asText());
        assertEquals("secret", decoded.get("client_secret").asText());
    }

}