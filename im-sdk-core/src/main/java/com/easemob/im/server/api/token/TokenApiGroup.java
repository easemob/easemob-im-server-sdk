package com.easemob.im.server.api.token;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.token.allocate.TokenAllocate;

public class TokenApiGroup {

    private TokenAllocate allocate;

    public TokenApiGroup(Context context) {
        this.allocate = new TokenAllocate(context);
    }

    public TokenAllocate allocate() {
        return this.allocate;
    }


}
