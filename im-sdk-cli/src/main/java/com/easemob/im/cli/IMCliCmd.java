package com.easemob.im.cli;

import ch.qos.logback.classic.Level;
import com.easemob.im.cli.cmd.*;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(name = "im",
        mixinStandardHelpOptions = true,
        subcommands = {
                BlockCmd.class,
                ContactCmd.class,
                CreateCmd.class,
                GetCmd.class,
                GroupCmd.class,
                NotificationCmd.class,
                UnblockCmd.class,
                UserCmd.class
        })
public class IMCliCmd implements InitializingBean {
    @Option(names = {"-v", "--verbose"})
    private boolean verbose;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.verbose) {
            Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
            root.setLevel(Level.DEBUG);
        }
    }
}
