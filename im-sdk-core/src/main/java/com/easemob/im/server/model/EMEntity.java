package com.easemob.im.server.model;

import java.util.Objects;

public class EMEntity {
    private EntityType entityType;
    private String id;

    protected EMEntity(EntityType entityType) {
        this.entityType = entityType;
    }

    public static EMEntity user(String username) {
        return new EMEntity(EntityType.USER).id(username);
    }

    public static EMEntity group(String id) {
        return new EMEntity(EntityType.GROUP).id(id);
    }

    public static EMEntity room(String id) {
        return new EMEntity(EntityType.ROOM).id(id);
    }

    public static EMEntity message(String id) {
        return new EMEntity(EntityType.MESSAGE).id(id);
    }

    public EMEntity id(String id) {
        this.id = id;
        return this;
    }

    public String id() {
        return this.id;
    }

    public EntityType entityType() {
        return this.entityType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        EMEntity emEntity = (EMEntity) o;
        return entityType == emEntity.entityType && Objects.equals(id, emEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityType, id);
    }

    public enum EntityType {
        USER,
        GROUP,
        ROOM,
        MESSAGE,
    }
}
