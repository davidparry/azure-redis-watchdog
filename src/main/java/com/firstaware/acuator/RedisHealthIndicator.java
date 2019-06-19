package com.firstaware.acuator;

import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class RedisHealthIndicator implements HealthIndicator {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisHealthIndicator.class);
    private StatefulRedisPubSubConnection<String, String> redisIdentityPublishConnection;

    @Autowired
    public RedisHealthIndicator(StatefulRedisPubSubConnection<String, String> redisIdentityPublishConnection) {
        this.redisIdentityPublishConnection = redisIdentityPublishConnection;
    }

    @Override
    public Health health() {
        if (check()) {
            return Health.up().withDetail("Ping", "PONG").build();
        } else {
            return Health.down()
                    .withDetail("Error Code", 1).build();
        }
    }

    public boolean check() {
        try {
            return redisIdentityPublishConnection.isOpen();
        } catch (Exception er) {
            LOGGER.error("Redis failure during health check {}", redisIdentityPublishConnection, er);
        }
        return false;
    }
}
