package com.livestockmanagementapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LivestockManagementApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LivestockManagementApiApplication.class, args);
    }

}
