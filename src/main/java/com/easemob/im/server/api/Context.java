package com.easemob.im.server.api;

import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.netty.http.client.HttpClient;

public interface Context {

    HttpClient getHttpClient();

    TokenProvider getTokenProvider();

    BearerAuthorization getBearerAuthorization();

    Codec getCodec();

}
