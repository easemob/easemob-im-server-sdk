package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.exception.EMLoadBalanceException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UniformRandomLoadBalancer implements LoadBalancer {
    @Override
    public Endpoint loadBalance(List<Endpoint> candidates) {
        if (candidates.isEmpty()) {
            throw new EMLoadBalanceException("endpoints is empty");
        }
        return candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
    }
}
