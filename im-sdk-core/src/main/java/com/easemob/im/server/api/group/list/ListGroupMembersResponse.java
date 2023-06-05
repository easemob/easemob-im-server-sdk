package com.easemob.im.server.api.group.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

//     "data": [{"member": "username1"}, {"owner": "username2"}]
public class ListGroupMembersResponse {
    @JsonProperty("data")
    private List<Map<String, String>> members;

    @JsonProperty("count")
    private Integer memberCount;

    @JsonProperty("params")
    private ListGroupParams paramsInfo;

    public Integer getMemberCount() {
        return memberCount;
    }

    public List<Map<String, String>> getMembers() {
        return members;
    }

    @JsonCreator
    public ListGroupMembersResponse(@JsonProperty("data") List<Map<String, String>> members,
            @JsonProperty("count") Integer memberCount,
            @JsonProperty("params") ListGroupParams paramsInfo) {
        this.members = members;
        this.memberCount = memberCount;
        this.paramsInfo = paramsInfo;
    }

    public ListGroupParams getParamsInfo() {
        return paramsInfo;
    }

    public static class ListGroupParams {
        @JsonProperty("pagesize")
        private List<String> pageSize;

        @JsonProperty("pagenum")
        private List<String> pageNum;

        @JsonCreator
        public ListGroupParams(@JsonProperty("pagesize") List<String> pageSize,
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
