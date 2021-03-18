package com.easemob.im.server.model;

import java.util.ArrayList;
import java.util.List;

public class EMRoom extends EMEntity {

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

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

    public String owner() {
        return this.owner;
    }

    public boolean needApprove() {
        return this.needApproveToJoin;
    }

    public int maxMembers() {
        return this.maxMembers;
    }

    public List<String> members() {
        return this.members;
    }

    public EMRoom(String roomId, String name, String description, boolean needApproveToJoin, String owner, int maxMembers, List<String> members) {
        super(EntityType.ROOM);
        super.id(roomId);

        this.name = name;
        this.description = description;
        this.needApproveToJoin = needApproveToJoin;
        this.owner = owner;
        this.maxMembers = maxMembers;
        this.members = members;
    }
}
