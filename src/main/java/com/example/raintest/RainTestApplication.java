package com.example.raintest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@Slf4j
@SpringBootApplication
public class RainTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RainTestApplication.class, args);
    }

}
