package com.easemob.im.server.api.block;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.block.user.BlockUser;
import com.easemob.im.server.api.block.user.GetUserBlocked;
import com.easemob.im.server.api.block.user.UnblockUser;
import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

public class BlockApiV1 {
    private Context context;

    public BlockApiV1(Context context) {
        this.context = context;
    }

    public BlockUser blockUsers(Publisher<String> usernames) {
        return new BlockUser(this.context, usernames);
    }

    public UnblockUser unblockUsers(Publisher<String> usernames) {
        return new UnblockUser(this.context, usernames);
    }

    public GetUserBlocked getUserBlocked() {
        return new GetUserBlocked(this.context);
    }
}
