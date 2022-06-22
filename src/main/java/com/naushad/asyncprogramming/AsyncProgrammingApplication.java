package com.naushad.asyncprogramming;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class AsyncProgrammingApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncProgrammingApplication.class, args);
    }

}
