package com.easemob.im.server.auth.token;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTokenRequestTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUserTokenRequestSerialized() throws JsonProcessingException {
        UserTokenRequest userTokenRequest = UserTokenRequest.of("username", "password");
        String encoded = this.mapper.writeValueAsString(userTokenRequest);
        JsonNode decoded = this.mapper.readTree(encoded);
        assertEquals("password", decoded.get("grant_type").asText());
        assertEquals("username", decoded.get("username").asText());
        assertEquals("password", decoded.get("password").asText());
    }

}