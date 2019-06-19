package com.firstaware.client.messaging;

public interface RedisPubSubService {

    boolean isConnected();

    boolean refresh();
}
