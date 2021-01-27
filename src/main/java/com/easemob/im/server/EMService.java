package com.easemob.im.server;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultContext;
import com.easemob.im.server.api.token.TokenApiGroup;
import com.easemob.im.server.api.user.UserApiGroup;

public class EMService {

    private TokenApiGroup tokenApiGroup;

    private UserApiGroup userApiGroup;

    public EMService(EMProperties properties) {
        Context context = new DefaultContext(properties);

        this.tokenApiGroup = new TokenApiGroup(context);
        this.userApiGroup = new UserApiGroup(context);
    }

    public TokenApiGroup token() {
        return this.tokenApiGroup;
    }

    public UserApiGroup user() {
        return this.userApiGroup;
    }

}
