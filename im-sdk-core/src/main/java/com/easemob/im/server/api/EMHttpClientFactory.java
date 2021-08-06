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

public class EMHttpClientFactory {

    private static final String HTTP_LOG_FORMAT = System.getenv("IM_HTTP_LOG_FORMAT");
    private static final AdvancedByteBufFormat FORMAT = "CONTENT".equals(HTTP_LOG_FORMAT) ?
            AdvancedByteBufFormat.TEXTUAL : AdvancedByteBufFormat.SIMPLE;

    public static HttpClient create(EMProperties properties) {
        ConnectionProvider connectionProvider =
                ConnectionProvider.create("easemob-sdk", properties.getHttpConnectionPoolSize());
        HttpClient httpClient = HttpClient.create(connectionProvider)
                .headers(headers -> headers.add("User-Agent",
                        String.format("EasemobServerSDK/%s", EMVersion.getVersion())))
                .wiretap("com.easemob.im.http", LogLevel.DEBUG, FORMAT);
        EMProxy proxyInfo = properties.getProxy();
        if (proxyInfo == null) {
            return httpClient;
        } else {
            final String username = proxyInfo.getUsername();
            final String password = proxyInfo.getPassword();
            final String ip = proxyInfo.getIp();
            final int port = proxyInfo.getPort();

            if (Strings.isNotBlank(username) && Strings.isNotBlank(password)) {
                return httpClient.proxy(
                        proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
                                .address(new InetSocketAddress(ip, port))
                                .username(username)
                                .password(p -> password)
                );
            } else {
                return httpClient.proxy(
                        proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
                                .address(new InetSocketAddress(ip, port))
                );
            }
        }
    }
}
