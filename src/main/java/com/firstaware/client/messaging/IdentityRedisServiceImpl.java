package com.firstaware.client.messaging;

import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class IdentityRedisServiceImpl extends BaseRedisPubSubService implements IdentityRedisService {
    public static final String TOPIC_NAME = "topic:azure:";
    private static final Logger LOGGER = LoggerFactory.getLogger(IdentityRedisServiceImpl.class);
    private StatefulRedisPubSubConnection<String, String> redisIdentityPublishConnection;


    @Autowired
    public IdentityRedisServiceImpl(StatefulRedisPubSubConnection<String, String> redisIdentityPublishConnection) {
        this.redisIdentityPublishConnection = redisIdentityPublishConnection;
    }

    @PostConstruct
    @Override
    public void init() {
        redisIdentityPublishConnection.addListener(this);
        redisIdentityPublishConnection.async().psubscribe(TOPIC_NAME + "*");
        LOGGER.debug("Subscribed to topic azure");
    }

    @PreDestroy
    public void shutdown() {
        redisIdentityPublishConnection.removeListener(this);
        redisIdentityPublishConnection.close();
    }

    @Override
    public void message(String channel, String message) {
        LOGGER.info("received on channel: {}  message received was the following: {}", channel, message);
    }

    @Override
    public void message(String pattern, String channel, String message) {
        LOGGER.info("received on pattern: {} for channel: {} message received was the following: {}", pattern, channel, message);
    }

    @Override
    public StatefulRedisPubSubConnection<String, String> statefulRedisPubSubConnection() {
        return this.redisIdentityPublishConnection;
    }
}
