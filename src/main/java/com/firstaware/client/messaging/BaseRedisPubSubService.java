package com.firstaware.client.messaging;

import io.lettuce.core.pubsub.RedisPubSubAdapter;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseRedisPubSubService extends RedisPubSubAdapter<String, String> implements RedisPubSubService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRedisPubSubService.class);

    @Override
    public boolean isConnected() {
        boolean flag = false;
        if (statefulRedisPubSubConnection() != null && statefulRedisPubSubConnection().isOpen()) {
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean refresh() {
        try {
            init();
            return true;
        } catch (Exception er) {
            LOGGER.error("Unable to refresh received an error from init method ", er);
            return false;
        }
    }

    public abstract StatefulRedisPubSubConnection<String, String> statefulRedisPubSubConnection();

    public abstract void init();
}
