package com.easemob.im.server.model;

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

    public EMRoom(String roomId, String name, String description, boolean needApproveToJoin, String owner, int maxMembers) {
        super(EntityType.ROOM);
        super.id(roomId);

        this.name = name;
        this.description = description;
        this.needApproveToJoin = needApproveToJoin;
        this.owner = owner;
        this.maxMembers = maxMembers;
    }
}
