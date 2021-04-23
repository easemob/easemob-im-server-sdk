package com.easemob.im.server.api.user.get;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class UserGetResponse {

    @JsonProperty("entities")
    private List<Map<String, Object>> entities;

    @JsonProperty("cursor")
    private String cursor;

    @JsonCreator
    public UserGetResponse(@JsonProperty("entities") List<Map<String, Object>> entities,
                           @JsonProperty("cursor") String cursor) {
        this.entities = entities;
        this.cursor = cursor;
    }

    public Map<String, Object> getEntities() {
        return entities.get(0);
    }

    //    public List<EMUser> getEMUsers() {
//        return this.entities.stream().map(UserResource::toEMUser).collect(Collectors.toList());
//    }
//
//    public EMUser getEMUser(String username) {
//        return this.entities.stream()
//            .filter(user -> user.getUsername().equals(username)).findFirst()
//            .map(UserResource::toEMUser).orElse(null);
//    }

    public String getCursor() {
        return this.cursor;
    }

}
