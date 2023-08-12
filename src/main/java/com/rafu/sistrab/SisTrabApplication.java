package com.rafu.sistrab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@ImportAutoConfiguration
@SpringBootApplication
public class SisTrabApplication {

    public static void main(String[] args) {
        SpringApplication.run(SisTrabApplication.class, args);
    }
}
