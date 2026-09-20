package com.focusforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FocusForgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(FocusForgeApplication.class, args);
    }
}
