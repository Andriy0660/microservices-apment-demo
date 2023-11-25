package com.andrii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GWApplication {
    public static void main(String[] args) {
        SpringApplication.run(GWApplication.class, args);
    }
}
