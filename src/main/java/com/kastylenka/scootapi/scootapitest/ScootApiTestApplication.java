package com.kastylenka.scootapi.scootapitest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ScootApiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScootApiTestApplication.class, args);
    }
}
