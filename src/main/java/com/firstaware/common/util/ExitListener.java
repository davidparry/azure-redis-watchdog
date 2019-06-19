package com.firstaware.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ExitListener implements ApplicationListener<ExitEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExitListener.class);
    @Value("${fa.shutdown.sleep.ms}")
    private int sleepMs = 500;

    @Async
    @Override
    public void onApplicationEvent(ExitEvent event) {
        LOGGER.info("Shutting down in {} (ms) triggering event {}", sleepMs, event);
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            LOGGER.warn("Sleep before shutdown was interrupted proceeding to shutdown" +
                    ". Shutdown procedure will continue.");
            Thread.currentThread().interrupt();
        }
         LOGGER.error("Would call System Exit with code {} but this is a test so waiting", event.getCode());

    }

}
