package com.easemob.im.cli;

import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import picocli.CommandLine;

@SpringBootApplication
public class IMCliApp implements CommandLineRunner, ExitCodeGenerator {
    @Autowired
    private CommandLine.IFactory factory;

    @Autowired
    private IMCliCmd cmd;

    @Autowired
    private EMService service;

    private int exitCode;

    public static void main(String[] args) {
        SpringApplication.run(IMCliApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        this.exitCode = new CommandLine(this.cmd, this.factory).execute(args);
    }

    @Override
    public int getExitCode() {
        return this.exitCode;
    }
}
