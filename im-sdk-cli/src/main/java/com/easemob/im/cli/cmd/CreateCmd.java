package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMException;
import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

@Component
@Command(name = "create", description = "Create a resource.")
public class CreateCmd {

    @Autowired
    private EMService service;

    @Command(name = "attachment", description = "Create an attachment.")
    public void createAttachment(@Option(names = "-f", description = "from file") Path file) {
        this.service.file().uploadFile(file, false)
            .doOnNext(id -> System.out.println(String.format("id: %s", id)))
            .doOnError(err -> System.out.println(String.format("error: %s", err.getMessage())))
            .onErrorResume(EMException.class, err -> Mono.empty())
            .block();
    }

}
