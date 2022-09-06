package com.easemob.im.server.model;

import java.util.List;
import java.util.Map;

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

    private Boolean mute;

    /**
     * The room's members
     */
    private List<Map<String, String>> members;

    /**
     * The room's member count
     */
    private Integer memberCount;

    /**
     * The room's custom
     */
    private String custom;

    /**
     * The room's created time stamp
     */
    private Long created;

    public EMRoom(String roomId, String name, String description, boolean needApproveToJoin,
            String owner, int maxMembers, Boolean mute, List<Map<String, String>> members,
            Integer memberCount, String custom, Long created) {
        super(EntityType.ROOM);
        super.id(roomId);

        this.name = name;
        this.description = description;
        this.needApproveToJoin = needApproveToJoin;
        this.owner = owner;
        this.maxMembers = maxMembers;
        this.mute = mute;
        this.members = members;
        this.memberCount = memberCount;
        this.custom = custom;
        this.created = created;
    }

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

    public Boolean mute() {
        return this.mute;
    }

    public List<Map<String, String>> members() {
        return this.members;
    }

    public Integer memberCount() {
        return this.memberCount;
    }

    public String custom() {
        return this.custom;
    }

    public Long created() {
        return this.created;
    }

    @Override public String toString() {
        return "EMRoom{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", needApproveToJoin=" + needApproveToJoin +
                ", owner='" + owner + '\'' +
                ", maxMembers=" + maxMembers +
                ", mute=" + mute +
                ", members=" + members +
                ", memberCount=" + memberCount +
                ", custom='" + custom + '\'' +
                ", created=" + created +
                '}';
    }
}
