package com.easemob.im.server;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultContext;
import com.easemob.im.server.api.token.TokenApiGroup;
import com.easemob.im.server.api.user.UserApiGroupV1;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.spi.LoggerContext;

public class EMService {
    private static final Logger log = LogManager.getLogger();

    private TokenApiGroup tokenApiGroup;

    private UserApiGroupV1 userApiGroupV1;

    public EMService(EMProperties properties) {
        printBanner();
        EMLog.setLogLevel(properties.getLogLevel());
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
