package com.easemob.im.cli.cmd;

import com.easemob.im.server.EMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Component
@Command(name = "contact", description = "Contact commands.")
public class ContactCmd {
    @Autowired
    private EMService service;

    @Command(name = "list", description = "List contacts of a user.")
    public void listContacts(@Parameters(index = "0", description = "the user's username") String username) {
        this.service.contact().list(username)
                .doOnNext(contact -> System.out.println(contact))
                .blockLast();
    }

    @Command(name = "add", description = "Add a contact to the user.")
    public void addContact(@Parameters(index = "0", description = "the user's username") String user,
                           @Parameters(index = "1", description = "the contact's username") String contact) {
        this.service.contact().add(user, contact)
                .doOnSuccess(ignored -> System.out.println("done"))
                .block();
    }

    @Command(name = "remove", description = "Remove a contact from the user.")
    public void removeContact(@Parameters(index = "0", description = "the user's username") String user,
                              @Parameters(index = "1", description = "the contact's username") String contact) {
        this.service.contact().remove(user, contact)
                .doOnSuccess(ignored -> System.out.println("done"))
                .block();
    }

}
