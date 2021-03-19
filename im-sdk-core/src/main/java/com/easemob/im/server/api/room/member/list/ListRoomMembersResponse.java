package com.easemob.im.server.api.room.member.list;

import com.easemob.im.server.model.EMPage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

//     "data": [{
//        "member": "user1"
//    },
//    {
//        "member": "user2"
//    }],
public class ListRoomMembersResponse {
    @JsonProperty("data")
    private List<Member> members;

    @JsonProperty("cursor")
    private String cursor;


    private static class Member {
        @JsonProperty("member")
        private String member;
        @JsonProperty("owner")
        private String owner;

        @JsonCreator
        public Member(@JsonProperty("member") String member,
                      @JsonProperty("owner") String owner) {
            this.member = member;
            this.owner = owner;
        }

        public String getUsername() {
            return this.member != null ? this.member : this.owner;
        }
    }

    public ListRoomMembersResponse(@JsonProperty("data") List<Member> members,
                                   @JsonProperty("cursor") String cursor) {
        this.members = members;
        this.cursor = cursor;
    }

    public List<String> getMembers() {
        return this.members.stream().map(Member::getUsername).collect(Collectors.toList());
    }

    public String getCursor() {
        return this.cursor;
    }

    public EMPage<String> toEMPage() {
        List<String> usernames = this.members.stream().map(Member::getUsername).collect(Collectors.toList());
        return new EMPage<>(usernames, this.cursor);
    }

}
