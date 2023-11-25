package com.andrii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.andrii.amqp",
        "com.andrii.smtpclient"
})
public class SmtpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmtpApplication.class, args);
    }
}
