package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.token.allocate.AppTokenRequest;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.netty.http.client.HttpClient;

public interface Context {

    HttpClient getHttpClient();

    EMProperties getProperties();

    TokenProvider getTokenProvider();

    Codec getCodec();

    ErrorMapper getErrorMapper();


}
