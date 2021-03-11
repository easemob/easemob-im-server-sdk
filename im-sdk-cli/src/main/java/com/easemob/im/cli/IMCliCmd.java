package com.easemob.im.cli;

import com.easemob.im.cli.cmd.*;
import org.springframework.stereotype.Component;
import picocli.AutoComplete;
import picocli.AutoComplete.GenerateCompletion;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

@Component
@Command(name = "im",
        mixinStandardHelpOptions = true,
        subcommands = {
                BlockCmd.class,
                ContactCmd.class,
                GroupCmd.class,
                NotificationCmd.class,
                UnblockCmd.class,
                UserCmd.class
        })
public class IMCliCmd {

}
