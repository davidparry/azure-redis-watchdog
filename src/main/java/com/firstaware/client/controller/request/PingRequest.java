package com.firstaware.client.controller.request;

public class PingRequest {
    private boolean refresh = false;
    private boolean decipher = false;

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    public boolean isDecipher() {
        return decipher;
    }

    public void setDecipher(boolean decipher) {
        this.decipher = decipher;
    }
}
