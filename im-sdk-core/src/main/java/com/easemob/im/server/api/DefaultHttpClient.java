package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMProxy;
import com.easemob.im.server.EMVersion;
import io.netty.handler.logging.LogLevel;
import org.apache.logging.log4j.util.Strings;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.ProxyProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.net.InetSocketAddress;

public class DefaultHttpClient implements Proxy {
    private HttpClient httpClient;

    @Override
    public HttpClient isSetProxy(EMProperties properties) {
        ConnectionProvider connectionProvider = ConnectionProvider.create("easemob-sdk", properties.getHttpConnectionPoolSize());
        this.httpClient = HttpClient.create(connectionProvider)
                .headers(headers -> headers.add("User-Agent", String.format("EasemobServerSDK/%s", EMVersion.getVersion())))
                .wiretap("com.easemob.im.http", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        EMProxy proxyInfo = properties.getProxy();
        if (proxyInfo == null) {
            return this.httpClient;
        } else {
            if (Strings.isNotBlank(proxyInfo.getUsername()) && Strings.isNotBlank(proxyInfo.getPassword())) {
                return this.httpClient.proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
                        .address(new InetSocketAddress(proxyInfo.getIp(), proxyInfo.getPort()))
                        .username(proxyInfo.getUsername())
                        .password(p -> proxyInfo.getPassword()));
            } else {
                return this.httpClient.proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
                        .address(new InetSocketAddress(proxyInfo.getIp(), proxyInfo.getPort())));
            }
        }
    }
}
