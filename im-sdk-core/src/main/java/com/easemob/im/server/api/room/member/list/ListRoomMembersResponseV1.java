package com.easemob.im.server.api.room.member.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

//     "data": [{"member": "username1"}, {"owner": "username2"}]
public class ListRoomMembersResponseV1 {
    @JsonProperty("data")
    private List<Map<String, String>> members;

    @JsonProperty("count")
    private Integer memberCount;

    @JsonProperty("params")
    private ListRomParams paramsInfo;

    public Integer getMemberCount() {
        return memberCount;
    }

    public List<Map<String, String>> getMembers() {
        return members;
    }

    @JsonCreator
    public ListRoomMembersResponseV1(@JsonProperty("data") List<Map<String, String>> members,
            @JsonProperty("count") Integer memberCount,
            @JsonProperty("params") ListRomParams paramsInfo) {
        this.members = members;
        this.memberCount = memberCount;
        this.paramsInfo = paramsInfo;
    }

    public ListRomParams getParamsInfo() {
        return paramsInfo;
    }

    public static class ListRomParams {
        @JsonProperty("pagesize")
        private List<String> pageSize;

        @JsonProperty("pagenum")
        private List<String> pageNum;

        @JsonCreator
        public ListRomParams(@JsonProperty("pagesize") List<String> pageSize,
                @JsonProperty("pagenum") List<String> pageNum) {
            this.pageSize = pageSize;
            this.pageNum = pageNum;
        }

        public String getPageSize() {
            return pageSize.get(0);
        }

        public String getPageNum() {
            return pageNum.get(0);
        }
    }

}
