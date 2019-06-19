package com.firstaware.client.messaging;

import com.firstaware.common.util.ExitEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class HeartbeatScheduleSend {
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatScheduleSend.class);
    private IdentityRedisService identityRedisService;
    private ApplicationEventPublisher eventPublisher;
    private Instant instant;
    private int timeToWaitForWatchDog = 10;

    @Autowired
    public HeartbeatScheduleSend(IdentityRedisService identityRedisService, ApplicationEventPublisher eventPublisher) {
        this.identityRedisService = identityRedisService;
        this.eventPublisher = eventPublisher;
        instant = Instant.now().plus(timeToWaitForWatchDog, ChronoUnit.MINUTES);
    }

    @Scheduled(fixedRateString = "${fa.heartbeat-time-interval}")
    public void check() {
        validatePubSubSessions();
    }

    private void validatePubSubSessions() {
        if (instant.isBefore(Instant.now())) {
            instant = Instant.now().plus(timeToWaitForWatchDog, ChronoUnit.MINUTES);
            LOGGER.debug("Usually between 10 minutes and 20 minutes for a Reconnect.");
            try {
                if (!this.identityRedisService.isConnected()) {
                    LOGGER.error("IdentityRedisService PubSub reported that it was not connected trying to refresh!");
                    if (!this.identityRedisService.refresh() || !this.identityRedisService.isConnected()) {
                        LOGGER.error("IdentityRedisService PubSub connection is closed! shutting down client-api service." +
                                        "The critical piece which is the Topic listener for this service is in failure {} "
                                , this.identityRedisService);
                        this.eventPublisher.publishEvent(new ExitEvent(this, 3));
                    }
                }
            } catch (Exception er) {
                LOGGER.error("Error took place when checking PubSub subscriptions shutting down client-api service." +
                        "The critical piece for this service is in failure! ", er);
                this.eventPublisher.publishEvent(new ExitEvent(this, 4));
            }
        }
    }
}
