package com.easemob.im.cli;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.easemob.im.cli.cmd.CreateCmd;
import com.easemob.im.cli.cmd.DeleteCmd;
import com.easemob.im.cli.cmd.GetCmd;
import com.easemob.im.cli.cmd.UpdateCmd;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(name = "im",
        mixinStandardHelpOptions = true,
        subcommands = {
                CreateCmd.class,
                GetCmd.class,
                DeleteCmd.class,
                UpdateCmd.class
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
