package com.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@EnableFeignClients
public class GymCrmApp {
    public static void main(String[] args) {
        SpringApplication.run(GymCrmApp.class);
    }
}
