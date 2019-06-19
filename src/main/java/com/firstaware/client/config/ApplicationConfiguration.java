package com.firstaware.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ApplicationConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public Gson gson() {
        return new GsonBuilder().serializeNulls().create();
    }

    @PostConstruct
    public void doneLoading() {
        LOG.info("The application and resources are loading...");
    }


}
