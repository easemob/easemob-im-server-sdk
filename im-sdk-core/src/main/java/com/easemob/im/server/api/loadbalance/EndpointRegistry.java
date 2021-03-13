package com.easemob.im.server.api.loadbalance;

import java.util.List;

public interface EndpointRegistry {
    List<Endpoint> endpoints();
}
