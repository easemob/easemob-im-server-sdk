package com.easemob.im.server.model;

import java.util.Objects;

public abstract class EMMessage extends EMEntity {

    private MessageType messageType;

    private EMEntity from;

    private EMEntity to;

    protected EMMessage(MessageType messageType) {
        super(EntityType.MESSAGE);
        this.messageType = messageType;
    }

    public MessageType messageType() {
        return this.messageType;
    }

    public EMMessage fromUser(String username) {
        this.from = EMEntity.user(username);
        return this;
    }

    public EMEntity from() {
        return this.from;
    }

    public EMMessage toUser(String username) {
        this.to = EMEntity.user(username);
        return this;
    }

    public EMMessage toGroup(String groupId) {
        this.to = EMEntity.group(groupId);
        return this;
    }

    public EMMessage toRoom(String roomId) {
        this.to = EMEntity.room(roomId);
        return this;
    }

    public EMEntity to() {
        return this.to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        EMMessage emMessage = (EMMessage) o;
        return Objects.equals(from, emMessage.from) && Objects.equals(to, emMessage.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from, to);
    }

    public enum MessageType {
        TEXT,
        IMAGE,
        AUDIO,
        VIDEO,
        LOCATION,
        FILE,
        COMMAND,
        CUSTOM,
    }

}
