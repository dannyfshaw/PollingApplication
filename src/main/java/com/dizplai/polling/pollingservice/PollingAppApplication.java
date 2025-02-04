package com.dizplai.polling.pollingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication
public class PollingAppApplication {
    public static void main(String[] args) {
        log.info("Starting PollingAppApplication");
        SpringApplication.run(PollingAppApplication.class, args);
    }
}

