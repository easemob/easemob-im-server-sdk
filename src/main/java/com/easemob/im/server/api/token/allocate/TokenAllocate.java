package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.model.EMToken;
import reactor.core.publisher.Mono;

public class TokenAllocate {

    private Context context;

    public TokenAllocate(Context context) {
        this.context = context;
    }

    public Mono<EMToken> forUser(String username, String password) {
        return this.context.getTokenProvider().fetchUserToken(username, password);
    }
}
