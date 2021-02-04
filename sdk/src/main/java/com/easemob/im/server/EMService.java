package com.easemob.im.server;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultContext;
import com.easemob.im.server.api.block.BlockApiV1;
import com.easemob.im.server.api.chatgroups.GroupApi;
import com.easemob.im.server.api.chatgroups.GroupsApi;
import com.easemob.im.server.api.notification.NotificationV1;
import com.easemob.im.server.api.token.TokenApiGroup;
import com.easemob.im.server.api.user.UserApiGroupV1;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EMService {

    private static final Logger log = LogManager.getLogger();

    private final Context context;

    private final TokenApiGroup tokenApiGroup;

    private final UserApiGroupV1 userApiGroupV1;

    private final NotificationV1 notificationV1;

    private final BlockApiV1 blockV1;

    public EMService(EMProperties properties) {
        printBanner();

        log.debug("EMService version: {}", EMVersion.getVersion());
        log.debug("EMService properties: {}", properties);

        this.context = new DefaultContext(properties);

        this.tokenApiGroup = new TokenApiGroup(this.context);
        this.userApiGroupV1 = new UserApiGroupV1(this.context);
        this.notificationV1 = new NotificationV1(this.context);
        this.blockV1 = new BlockApiV1(this.context);
    }

    public TokenApiGroup tokenV1() {
        return this.tokenApiGroup;
    }

    public UserApiGroupV1 userV1() {
        return this.userApiGroupV1;
    }

    public NotificationV1 notificationV1() {
        return this.notificationV1;
    }

    public BlockApiV1 blockV1() {
        return this.blockV1();
    }

    public GroupsApi groups() {
        return new GroupsApi(this.context);
    }

    public GroupApi group(String groupId) {
        return new GroupApi(this.context, groupId);
    }

    private void printBanner() {
        String banner = "                                                                                            \n" +
            "            ////////\\\\      ______  ___    _____   ______  __  ___  ____    ____    ______ \n" +
            "          ///       ///    / ____/ /   |  / ___/  / ____/ /  |/  / / __ \\  / __ )  / ____/ \n" +
            "        ///  //////////   / __/   / /| |  \\__ \\  / __/   / /|_/ / / / / / / __  | / __/   \n" +
            "         ///             / /___  / ___ | ___/ / / /___  / /  / / / /_/ / / /_/ / / /___     \n" +
            "          //////////    /_____/ /_/  |_|/____/ /_____/ /_/  /_/  \\____/ /_____/ /_____/    \n" +
            "                                                                                         ";

        System.out.println(banner);
    }

}
