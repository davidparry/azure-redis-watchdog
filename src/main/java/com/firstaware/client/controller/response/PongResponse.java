package com.firstaware.client.controller.response;

public class PongResponse {

    private boolean valid;

    public PongResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }
}
