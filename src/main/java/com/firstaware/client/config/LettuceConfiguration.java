package com.firstaware.client.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class LettuceConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(LettuceConfiguration.class);

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.builder()
                .build();
    }

    @Bean
    public AzureLettuceProperties redisProperties() {
        return new AzureLettuceProperties();
    }


    @Bean(name = "redisClient", destroyMethod = "shutdown")
    public RedisClient redisClient(AzureLettuceProperties redisProperties, ClientResources clientResources) {

        RedisClient redisClient = RedisClient
                .create(clientResources, redisProperties.getPrimary());
        redisClient.setOptions(ClientOptions.builder()
                .pingBeforeActivateConnection(true)
                .suspendReconnectOnProtocolFailure(false)
                .autoReconnect(true)
                .build());
        return redisClient;
    }

    @Bean(name = "redisPublishConnection")
    @Scope(value = "singleton")
    public StatefulRedisPubSubConnection<String, String> redisPublishConnection(RedisClient redisClient) {
        StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();
        return connection;
    }

    @Bean(name = "redisIdentityPublishConnection")
    @Scope(value = "singleton")
    public StatefulRedisPubSubConnection<String, String> redisIdentityPublishConnection(RedisClient redisClient) {
        StatefulRedisPubSubConnection<String, String> connection = redisClient.connectPubSub();
        LOGGER.debug("Timeout for this connection is {}", connection.getTimeout());
        return connection;
    }

}
