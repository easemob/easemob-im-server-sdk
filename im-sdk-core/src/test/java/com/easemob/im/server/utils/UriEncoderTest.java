package com.easemob.im.server.utils;

import io.netty.handler.codec.http.QueryStringEncoder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UriEncoderTest {

    private static final String CURSOR_VALUE = "asdjhbsaiuyhishfdxhsk";

    @Test
    public void encodeUri() {
        String uriString = next(123, CURSOR_VALUE);
        assertEquals("/users?limit=123&cursor=asdjhbsaiuyhishfdxhsk", uriString);
    }

    private String next(int limit, String cursor) {
        QueryStringEncoder encoder = new QueryStringEncoder("/users");
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        return encoder.toString();
    }
}
