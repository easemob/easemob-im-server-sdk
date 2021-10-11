package com.easemob.im.server;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EMErrorResponse {
    @JsonProperty("error_description")
    private String errorDescription;

    public EMErrorResponse(@JsonProperty("error_description") String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }
}
