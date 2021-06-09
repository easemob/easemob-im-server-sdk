package com.easemob.im.server.api.loadbalance;


public interface EndPointProviderSelector {
    EndpointProvider selectProvider();
}
