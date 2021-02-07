package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "contact", description = "Contact commands.")
public class ContactCmd {
    @Autowired
    private EMService service;

    public
}
