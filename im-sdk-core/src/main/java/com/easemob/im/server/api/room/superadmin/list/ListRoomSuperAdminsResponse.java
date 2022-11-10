package com.easemob.im.server.api.room.superadmin.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListRoomSuperAdminsResponse {
    @JsonProperty("data")
    private List<String> admins;

    @JsonProperty("params")
    private ListRoomSuperAdminParams paramsInfo;

    @JsonCreator
    public ListRoomSuperAdminsResponse(@JsonProperty("data") List<String> admins) {
        this.admins = admins;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public ListRoomSuperAdminParams getParamsInfo() {
        return paramsInfo;
    }

    public static class ListRoomSuperAdminParams {
        @JsonProperty("pagesize")
        private List<String> pageSize;

        @JsonProperty("pagenum")
        private List<String> pageNum;

        @JsonCreator
        public ListRoomSuperAdminParams(@JsonProperty("pagesize") List<String> pageSize,
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
