package com.easemob.im.server.api.contact.user;

import com.easemob.im.server.api.contact.ContactUserPageResource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContactUserPageListResponse {
    @JsonProperty("data")
    private Contacts data;

    @JsonCreator
    public ContactUserPageListResponse(@JsonProperty("data") Contacts data) {
        this.data = data;
    }

    public Contacts getData() {
        return data;
    }

    public static class Contacts {
        @JsonProperty("contacts")
        private List<ContactUserPageResource> contacts;

        @JsonCreator
        public Contacts(@JsonProperty("contacts") List<ContactUserPageResource> contacts) {
            this.contacts = contacts;
        }

        public List<ContactUserPageResource> getContacts() {
            return contacts;
        }
    }
}
