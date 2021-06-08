package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import reactor.netty.http.client.HttpClient;

public interface Proxy {
    HttpClient isSetProxy(EMProperties properties);
}
