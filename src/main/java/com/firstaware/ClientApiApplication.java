package com.firstaware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClientApiApplication {
    private static final Logger logger = LoggerFactory.getLogger(ClientApiApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(ClientApiApplication.class, args);
    }

}
