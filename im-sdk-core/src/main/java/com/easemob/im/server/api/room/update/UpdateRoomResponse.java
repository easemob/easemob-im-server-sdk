package com.easemob.im.server.api.room.update;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateRoomResponse {

    @JsonProperty("data")
    private Wrapper wrapper;

    public static class Wrapper {
        @JsonProperty("description")
        private Boolean description;
        @JsonProperty("maxusers")
        private Boolean maxMembers;
        @JsonProperty("groupname")
        private Boolean name;

        public Wrapper(@JsonProperty("description") Boolean description,
                       @JsonProperty("maxusers") Boolean maxMembers,
                       @JsonProperty("groupname") Boolean name) {
            this.description = description;
            this.maxMembers = maxMembers;
            this.name = name;
        }

        public Boolean getDescription() {
            return this.description;
        }

        public Boolean getMaxMembers() {
            return this.maxMembers;
        }

        public Boolean getName() {
            return this.name;
        }
    }

    @JsonCreator
    public UpdateRoomResponse(@JsonProperty("data") Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public boolean nameUpdated() {
        return this.wrapper.getName() != null && this.wrapper.getName();
    }

    public boolean descriptionUpdated() {
        return this.wrapper.getDescription() != null && this.wrapper.getDescription();
    }

    public boolean maxMembersUpdated() {
        return this.wrapper.getMaxMembers() != null && this.wrapper.getMaxMembers();
    }
}
