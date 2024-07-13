package com.rabatownik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabatownikApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabatownikApplication.class, args);
    }
}
