package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.codec.JsonCodec;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractApiTest {
    protected ObjectMapper objectMapper;

    protected MockingHttpServer server;

    protected EMProperties properties;

    protected MockingContext context;

    protected JsonCodec codec;

    protected AbstractApiTest() {

        this.objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        this.server = MockingHttpServer.builder().build();

        this.properties = EMProperties.builder()
                .setAppkey("easemob#demo")
                .setClientId("clientId")
                .setClientSecret("clientSecret")
                .build();

        this.context = new MockingContext(properties, this.server.uri());

        this.codec = new JsonCodec();
    }
}
