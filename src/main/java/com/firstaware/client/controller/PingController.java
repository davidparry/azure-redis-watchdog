package com.firstaware.client.controller;


import com.firstaware.client.controller.response.PongResponse;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.firstaware.client.controller.PingController.PING_PATH;
import static com.firstaware.client.messaging.IdentityRedisServiceImpl.TOPIC_NAME;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(PING_PATH)
public class PingController {
    public static final String PING_PATH = "/ping";
    private static final Logger LOG = LoggerFactory.getLogger(PingController.class);

    @Autowired
    private StatefulRedisPubSubConnection<String, String> redisPublishConnection;

    @ResponseStatus(code = HttpStatus.ACCEPTED)
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PongResponse> ping() throws BindException {
        LOG.debug("Ping Request");
        PongResponse pongResponse = new PongResponse(true);
        try {
            RedisPubSubAsyncCommands<String, String> async
                    = redisPublishConnection.async();
            RedisFuture<Long> future = async.publish(TOPIC_NAME + "ID", Instant.now().toString() + " Message for Azure Topic");
            future.await(1, TimeUnit.HOURS);
            String error = future.getError();
            if (StringUtils.isNotBlank(error)) {
                LOG.error("Error on publish was {}", error);
            }
            Long value = future.get();
            LOG.debug("Value returned from publish was {}", value);

        } catch (Exception er) {
            pongResponse = new PongResponse(false);
            LOG.error("Error in publishing ", er);
        }

        return ResponseEntity.accepted().body(pongResponse);
    }

}
