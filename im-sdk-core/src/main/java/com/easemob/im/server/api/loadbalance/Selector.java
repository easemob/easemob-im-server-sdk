package com.easemob.im.server.api.loadbalance;


public interface Selector {
    EndpointProvider selectProvider();
}
