package com.easemob.im.server.model;

import java.util.ArrayList;
import java.util.List;

public class EMRoom {
    /**
     * The room's id.
     */
    private String id;

    /**
     * The room's name.
     */
    private String name;

    /**
     * The room's description.
     */
    private String description;

    /**
     * Does new member need approve of admins to join this room.
     */
    private boolean needApproveToJoin;

    /**
     * The owner's username.
     */
    private String owner;

    /**
     * How many members can this room hold.
     */
    private int maxMembers;

    /**
     * The members of this room.
     */
    private List<String> members;

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getOwner() {
        return this.owner;
    }

    public boolean getNeedApprove() {
        return this.needApproveToJoin;
    }

    public int getMaxMembers() {
        return this.maxMembers;
    }

    public List<String> getMembers() {
        return this.members;
    }

    public static EMRoom of(String id) {
        EMRoom room = new EMRoom(id);
        return room;
    }

    public EMRoom withName(String name) {
        this.name = name;
        return this;
    }

    public EMRoom withDescription(String description) {
        this.description = description;
        return this;
    }

    public EMRoom withNeedApproveToJoin(boolean needApproveToJoin) {
        this.needApproveToJoin = needApproveToJoin;
        return this;
    }

    public EMRoom withOwner(String username) {
        this.owner = username;
        return this;
    }

    public EMRoom withMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
        return this;
    }

    public EMRoom withMember(String username) {
        this.members.add(username);
        return this;
    }

    public EMRoom withoutMember(String username) {
        this.members.remove(username);
        return this;
    }

    private EMRoom(String id) {
        this.id = id;
        this.members = new ArrayList<>();
    }
}
