package com.easemob.im.server;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultContext;
import com.easemob.im.server.api.token.TokenApiGroup;
import com.easemob.im.server.api.user.UserApiGroupV1;

public class EMService {

    private TokenApiGroup tokenApiGroup;

    private UserApiGroupV1 userApiGroupV1;

    public EMService(EMProperties properties) {
        Context context = new DefaultContext(properties);

        this.tokenApiGroup = new TokenApiGroup(context);
        this.userApiGroupV1 = new UserApiGroupV1(context);
    }

    public TokenApiGroup token() {
        return this.tokenApiGroup;
    }

    public UserApiGroupV1 user() {
        return this.userApiGroupV1;
    }

}
