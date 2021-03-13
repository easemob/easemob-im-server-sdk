package com.easemob.im.server.api.loadbalance;

import java.util.List;

public interface LoadBalancer {
    Endpoint loadBalance(List<Endpoint> candidates);
}
